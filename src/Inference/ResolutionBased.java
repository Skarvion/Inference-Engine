package Inference;

import Inference.Converter.CNFConverter;
import Knowledge.KnowledgeBase;
import Knowledge.Sentence;

import java.util.*;

/**
 * Resolution based inference method, at the moment it does not work all the time
 */
public class ResolutionBased extends InferenceMethod {

    @Override
    public boolean entails(Sentence query) {
        List<String> cnfList = new ArrayList<>();
        for (Sentence s : KnowledgeBase.getSentenceList()) {
            List<String> steps = CNFConverter.process(s.getSentence());
            cnfList.add(steps.get(steps.size() - 1));
        }

        while (true) {
            for (int i = 0; i < cnfList.size(); i++) {
                for (int j = 0; j < cnfList.size(); j++) {
                    if (i == j) continue;

                    System.out.println("Pair::");
                    System.out.println(cnfList.get(i));
                    System.out.println(cnfList.get(j));
                    String newClause = plResolve(cnfList.get(i), cnfList.get(j), query);
                    System.out.println(newClause);
                    if (newClause.equals("")) return true;
                    else {
                        for (String cnf : cnfList) {
                            if (equalClause(cnf, newClause)) {
                                System.out.println(cnf);
                                return false;
                            }
                        }
                        cnfList.add(newClause);
                    }
                }
            }
        }
    }

    private boolean equalClause(String s1, String s2) {
        Set<String> clause1positives = new HashSet<>();
        Set<String> clause1negatives = new HashSet<>();

        Set<String> clause2positives = new HashSet<>();
        Set<String> clause2negatives = new HashSet<>();

        String[] symbolIn1 = s1.split("\\\\/");
        String[] symbolIn2 = s2.split("\\\\/");

        for (String s : symbolIn1) {
            if (s.contains("~")) clause1negatives.add(s.replaceAll("~", ""));
            else clause1positives.add(s);
        }

        for (String s : symbolIn2) {
            if (s.contains("~")) clause2negatives.add(s.replaceAll("~", ""));
            else clause2positives.add(s);
        }

        Iterator<String> iteratorPositive = clause1positives.iterator();
        while (iteratorPositive.hasNext()) {
            String s = iteratorPositive.next();
            if (clause2positives.contains(s)) {
                iteratorPositive.remove();
            }
        }

        Iterator<String> iteratorNegative = clause1negatives.iterator();
        while (iteratorNegative.hasNext()) {
            String s = iteratorNegative.next();
            if (clause2negatives.contains(s)) {
                iteratorNegative.remove();
            }
        }

        Set<String> totalPositive = new HashSet<>();
        Set<String> totalNegative = new HashSet<>();

        totalPositive.addAll(clause1positives);
        totalPositive.addAll(clause2positives);
        totalNegative.addAll(clause1negatives);
        totalNegative.addAll(clause2negatives);

        if (totalPositive.size() + totalNegative.size() > 0) return false;
        else return true;
    }

    private String plResolve(String s1, String s2, Sentence query) {
        Set<String> clause1positives = new HashSet<>();
        Set<String> clause1negatives = new HashSet<>();

        Set<String> clause2positives = new HashSet<>();
        Set<String> clause2negatives = new HashSet<>();

        String[] symbolIn1 = s1.split("\\\\/");
        String[] symbolIn2 = s2.split("\\\\/");

        for (String s : symbolIn1) {
            if (s.contains("~")) clause1negatives.add(s.replaceAll("~", ""));
            else clause1positives.add(s);
        }

        for (String s : symbolIn2) {
            if (s.contains("~")) clause2negatives.add(s.replaceAll("~", ""));
            else clause2positives.add(s);
        }

        Iterator<String> i1 = clause1positives.iterator();
        while (i1.hasNext()) {
            String s = i1.next();
            if (clause2negatives.contains(s)) {
                i1.remove();
                clause2negatives.remove(s);
            }
        }


        Iterator<String> i2 = clause2positives.iterator();
        while (i2.hasNext()) {
            String s = i2.next();
            if (clause1negatives.contains(s)) {
                i2.remove();
                clause1negatives.remove(s);
            }
        }

        Set<String> totalPositive = new HashSet<>();
        Set<String> totalNegative = new HashSet<>();
        totalPositive.addAll(clause1positives);
        totalPositive.addAll(clause2positives);
        totalNegative.addAll(clause1negatives);
        totalNegative.addAll(clause2negatives);

        String rawSymbol = query.getSentence();
        if (rawSymbol.contains("~")) {
            rawSymbol.replaceAll("~", "");
            totalNegative.remove(rawSymbol);
        }
        else {
            totalPositive.remove(rawSymbol);
        }

        if (totalPositive.size() + totalNegative.size() == 0) {
            return "";
        }
        else {
            String result = "";
            Iterator<String> iteratorPositive = totalPositive.iterator();
            while (iteratorPositive.hasNext()) {
                result += iteratorPositive.next();
                if (iteratorPositive.hasNext()) {
                    result += "\\/";
                }
            }

            if ((totalPositive.size() > 0) && (totalNegative.size() > 0)) {
                result += "\\/";
            }

            Iterator<String> iteratorNegative = totalNegative.iterator();
            while (iteratorNegative.hasNext()) {
                result += "~" + iteratorNegative.next();
                if (iteratorNegative.hasNext()) {
                    result += "\\/";
                }
            }

            return result;

        }

    }
}
