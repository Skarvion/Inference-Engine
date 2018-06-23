================================================
        INTRODUCTION TO AI: ASSIGNMENT 2
================================================

Name: William Saputra
Student ID: 100039787
Group: Individual

FEATURES
================
This program is an inference engine that parse in a text file of knowledge base in horn forms and query. The program is run from command line. For the inference method, there are 3 methods that is being used these are:
Truth Table
Forward Chaining
Backward Chaining

To invoke the inference engine, the following command lines can be used:
iengine [method] [filename]

The method denotes what method to use to entail the knowledge base. To use the list of method, use the following arguments:

Truth Table:		TT
Forward Chaining:	FT
Backward Chaining:	BC

Filename is the name of the text file where its list of tell sentence and ask symbol will be parsed into knowledge base and query respectively.

An example of running the argument is as follow:

iengine TT test1.txt

This runs the inference engine using the Truth Table method and parse a text file called test1.txt

BUGS
================
Known bugs include the one included in the missing feature.

MISSING
================
The program was intended to be able to run resolution inference method and its accompanying CNF conversion feature. Unfortunately, it does not always properly run as intended and the CNF conversion is only able to convert from Horn-form successfully. These cannot be fixed in time. Therefore, it is considered as incomplete feature. The source code for attempting to work this feature is still present, and the program still has the argument to see the inference in action, albeit it may stuck in permanent loop.

To invoke resolution inference, use the following argument, be warned, it might loop infinitely:

Resolution Based:   RB 

TEST CASES
================
To validate the correctness of this program, test cases were created and its result is compared:

Test Case #1
TELL
p2=> p3; p3 => p1; c => e; b&e => f; f&g => h; p1=>d; p1&p3 => c; a; b; p2;
ASK
d

+Result
    Truth Table
    YES 3
    Forward Chaining
    YES a, b, p2, p3, p1, d
    Backward Chaining
    YES p2, p3, p1, d

Test Case #2
TELL
p1&p2&p3=> p4; p5&p6 => p4; p1 => p2; p1&p2 => p3; p5&p7 => p6; p1; p4;
ASK
P7

+Result
    Truth Table
    NO
    Forward Chaining
    NO
    Backward Chaining
    NO

Test Case #3
TELL
P=>Q; L&M => P; B&L => M; A& P => L; A&B =>L; A; B;
ASK
Q

+Result
    Truth Table
    YES 1
    Forward Chaining
    YES A, B, L, M, P, Q
    Backward Chaining
    YES B, L, M, P, Q

Test Case #4
TELL
C&B => D; A =>F; D & A &F=> E; E=>G; A; B; C;
ASK
G

+Result
    Truth Table
    YES 1
    Forward Chaining
    YES A, B, C, F, D, E, G
    Backward Chaining
    YES A, F, E, G

Test Case #5
TELL
x1=>x2;x2=>x3;x3=>x1; x3:
ASK
x1

+Result
    Truth Table
    YES 1
    Forward Chaining
    YES x3, x1
    Backward Chaining
    YES x3, x1

ACKNOWLEDGEMENT / RESOURCES
================
I used multiple websites and books to help me solve this problem.

Book:
Russell, S., Norvig, P. and Davis, E. (2010). Artificial intelligence. 3rd ed. Upper Saddle River, NJ: Prentice Hall.

The book gives a very comprehensive explanation about inference logic. It helps to provide the background knowledge to complete this assignment.

Videos:
Forward and Backward Chaining:
https://www.youtube.com/watch?v=EZJs6w2YFRM 
Resolution Inference:
https://www.youtube.com/watch?v=PMm5Mat0MRA 

These videos are made by Francisco Iacobelli which helps to explain how these inference engine method works. It is easier to understand the algorithm with better illustration.


Source Code:
Forward Chaining Example Code
http://snipplr.com/view/56296/ai-forward-chaining-implementation-for-propositional-logic-horn-form-knowledge-bases/ 
Backward Chaining Example Code
http://snipplr.com/view/56297/ai-backward-chaining-implementation-for-propositional-logic-horn-form-knowledge-bases/ 
Inference Engine Example Code
https://github.com/Manu343726/PracticasIA 

I have looked up on other people’s work to solve the inference engine problem. I did not copy their code to my source. Instead, I use it to better understand and see a better example. Hence, my code is the combination of both from the book, and their source codes with my own framework and improvement.
