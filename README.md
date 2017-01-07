# Random-text-generator

Introduction and Background

In this assignment you will be using data structures and algorithms we have covered recently to write a program that has some interesting, and maybe even entertaining, output. Also, the design will be less constrained than in past assignments: you will have the opportunity to decide on the exact interface and representation for your classes. You'll also get some practice with command-line arguments and text file processing.
In this assignment you will have a chance to use one of the more powerful Java tools we have seen, Maps. You'll also be using command-line arguments and file processing.

The program we'll create here will write an essay automatically! Or put less grandly, it will generate random text. To make the result sound more like real English, than, say just choosing random words from the dictionary, we're going to use some sample text to give us information about likely words and word sequences.

We'll call the sample text document we're using the source text. Consider generating text using the frequencies of occurrences of words in the source text to choose the next word to generate in our random text. E.g., if "the" occurs with probability 0.03 in the source text, then we will generate it with that probability. This could be done easily by every time just choosing a word at random from the source text. We call random text generated this way order-0 text. We could get something that sounds a little more like English by taking into account sequences of words from the source text. For order-1 text, once we generate a word, w, we generate the next word based on its probability of following w in the source text.

Here is an example of some order-1 text that uses My Man Jeeves by P.G. Wodehouse as the source text:

"Unsuitable for me to what the ear of omniscience. As a topping place is a wealthy bird, so mixed up by the "Lincolnshire." I looked fine in a grey check suit, and so forth. Brainy coves. Corky when I decided that one of difficult for the Park, and settled down Washington Square--artists and I'm a lot first. This makes it wasn't long cosy chats about clothes is what the game.
This passage sounds somewhat nonsensical.
We could generalize this, using order-k word sequences, which we'll call prefixes here. Every k-word prefix we generate is a sequence found in the source text. We generate the next word by looking at the frequency of all of the successors of this prefix in the source text.

Here is an example of order-2 text from the same source:

I'm a bit instead of going back and having long cosy chats about the thing with aunt. So I sent Jeeves out to find a decent apartment, and settled down for a bit of exile. I'm bound to say that New York's a topping place to be called _More American Birds_. When he has put the cat out and locked up the office for the 'Lincolnshire.'" He shook his head. "I'd rather not, sir." "But it's the straight goods. I'm going to make a hit. Meanwhile, by using the utmost tact and persuasiveness, he was going to put my shirt on him."
We see that the order-2 text is more grammatical, although a bit off from what a person would write.
We'll go through a detailed example of generating words this way later in the document . (This is an example of something called a Markov process.)

Note: Parts of the Word-level Markov generation , Approach, and Data Files sections of this document were adapted from a description of a character-level version of this assignment written by Joe Zachary at Univ. of Utah.

The assignment files

Getting the assignment files. Make a PA4 directory and cd into it. The following command will copy the assignment files, including some data files, into your directory. (Note: The larger data files will be linked rather than copied.)
gmake -f ~csci455/assgts/pa4/Makefile getfiles
The files in bold below are ones you create and submit. The ones not in bold are ones that you will use, but not modify. More details about the java classes below are in the section on class design. The files are:

GenText.java Contains the main method for this assignment.
Prefix.java A Prefix class that you are going to write.
RandomTextGenerator.java A RandomTextGenerator class that you are going to write.
README See section on Submitting your program for what to put in it. Before you start the assignment please read the following statement which you will be "signing" in the README:
"I certify that the work submitted for this assignment does not violate USC's student conduct code. In particular, the work is my own, not a collaboration, and does not involve code created by other people, with the exception of the resources explicitly mentioned in the CS 455 Course Syllabus. And I did not share my solution or parts of it with other students in the course."
Makefile A file with rules for the "gmake" command. In particular this Makefile has rules to download the data files and itself, and for submitting the program. See section about submitting your program for how submit your program using "gmake".
data A directory with some data files to test GenText on. When you download the files for the assignment, the two large data files from this directory will be symbolically linked to the copy on the course account. That is, they appear as if they are in your directory, but they don't take up space on your account because it uses a pointer to our version. The small data files will get copied to your account. More about data files in the section about that.
The assignment

You will be implementing a program which uses a word-level Markov chain to generate random text, using data from a sample text document, as described in the the introduction, above, and described in more detail in the next section. For order-k text, here we're going to call "k" the prefixLength, and the previous k words generated we'll call the current prefix.
A run of your program,

 java GenText [-d] prefixLength numWords sourceFile outFile 
generates numWords words of prefixLength-order text using sourceFile as the source document. The generated words are written to outFile (not the console). The square brackets ([]) above denote optional arguments to your program (the square brackets themselves are not part of the command line). You have one optional argument here, "-d", the presence of which means the program will be run in debugging mode; we'll describe what that means in the section on debugging mode.
For our purposes, we don't have to treat punctuation or other characters specially: any sequence of non-whitespace characters is considered a word. So, for example, the words "Dog", "dog", "dog.", and "dog!" are considered distinct, and the frequency that each occurs in the source file, and which words generally follow each of them may be different.

The format of outFile is as follows: there will be one space between each word generated, with up to 80 characters generated per line (the last character on a line should not be a space). All lines of output should contain as many words as possible to be full, but not more than 80 characters, except the last line. I.e., the text will be flush left, ragged right, with a maximum line-length of 80 characers. Note that you are not required to produce justified text (i.e., the output for each line doesn't have to be exactly 80 characters). Also, you do not have to break up words by syllable over multiple lines.

Here is an example of this kind of of formatting. Just for the example we'll assume a max of 30 characters per line, to make it easy to count characters. The text sample used is a sentence from this document. The first line in italics below indicates the column number of the characters below it:


123456789012345678901234567890

For our purposes, we don't
have to treat punctuation or
other characters specially:
any sequence of non-whitespace
characters is considered a
word.
Additional program requirements are described in the following sections and summarized here:

Efficiency. you will get more credit if you have an efficient solution. A few approaches and their complexities are discussed in the approach section.
Class design. you are required to design and implement the classes discussed in the section on class design. We will also be evaluating the quality of the fleshed out version of this design.
Error checking. you are required to do the error checking spelled out in the section on that.
README. as usual, you are required to submit a README file. See the end of this document for what needs to go in it for this assignment.
Style / Documentation / Design. Also as usual, your program will be evaluated on style and documentation. See the section on grading criteria for more details.

Word-level Markov text generation

For generating text, you should initially choose prefixLength consecutive words at random from the sample text and use them as the initial prefix (we'll call this a random prefix) in the random writing process outlined earlier. (The program does not generate the words in this initial prefix.) Your program should then write numWords words to outFile. Each of these words should be chosen based on the current prefix. (Each time a word w is written to outFile, the prefix is updated by removing its first word and appending w to the end.)

For example, suppose that prefixLength = 2 and the sample file contains

I like the big blue dog better than the big elephant with the big blue hat on his tusk.
Here is how the first three words might be chosen:
A two-word sequence is chosen at random to become the initial prefix. Let's suppose that "the big" is chosen.
The first word must be chosen based on the probability that it follows the prefix (currently "the big") in the source. The source contains three occurrences of "the big". Two times it is followed by "blue", and once it is followed by "elephant". Thus, the next word must be chosen so that there is a 2/3 chance that "blue" will be chosen, and a 1/3 chance that "elephant" will be chosen. Let's suppose that we choose "blue" this time.
The next word must be chosen based on the probability that it follows the prefix (currently "big blue") in the source. The source contains two occurrences of "big blue". Once it is followed by "dog", and the other time it is followed by "hat". Thus, the next word must be chosen so that there is a 50-50 probability of choosing "dog" vs. "hat". Let's suppose that we choose "hat" this time.
The next word must be chosen based on the probability that it follows the prefix (currently "blue hat") in the source. The source contains only one occurrence of "blue hat", and it is followed by "on". Thus, the next word must be "on" (100% probability).
Thus, the first three words in the output text would be "blue hat on".
Note that the words in the initial prefix are not part of the generated text.

If your function ever gets into a situation in which there are no words to choose from (which can happen if the only occurrence of the current prefix is at the exact end of the sample), your program should pick a new random prefix (i.e., the same way it chose the initial prefix) and continue. As with the initial prefix, you do not generate the words in this random prefix. (This is so we can control the exact number of words to be generated.)

So you don't fail on the very first try (or get into an infinite loop because you keep choosing a random prefix at the end of the file) you should choose your random prefix so that you know there is at least one suffix following it (i.e., so the last word in the prefix is not the last word in the file). In fact, to make generating text doable at all, one user requirement is that the number of words in the source file is at least one more than prefixLength (see section on error checking for more details).

For generating random numbers, use the Java Random class. Because of previous confusion about this class, we'll remind you again here, you should only create one instance of this object, and then after that use the same object to generate all random numbers needed.

You do not have to handle a prefixLength of 0 (i.e., order-0 text generation). This is another one of the input errors you'll be checking for.


Approach

There is a simple way to attack this problem. Create an ArrayList of strings that contains all of the words from the sample file. To choose the next word, find each occurrence of the prefix in the sample and store the word that follows that occurrence into another ArrayList of strings (let's call it the successors). When you have found all occurrences, choose a word at random from the successors array. You will then shift this word into the prefix so that the prefix now contains the last prefixLength words generated.
If the length (in number of words) of the sample text is s and the length of the text to generate is g, the above approach takes time O(s * g). (To simplify the analysis, we're considering the length of a prefix as a constant.) If instead you use a HashMap to make this search more efficient, you can reduce this to O(s + g). This involves preprocessing the source text to create the map before you start generating words.

Hint about using a HashMap: remember that to use a class as the KeyType in a HashMap it must implement equals and hashCode.

You may choose between these two approaches, or others you may devise using data structures and algorithms we have covered in class. More credit will be given for a more efficient solution. By efficient, we mean in big-O terms above; we will not be timing your programs. You need to document your approach in your README file.

Class design

Unlike the previous programs in this course, this time you are going to design your own classes, with some guidance. Subsequently, part of your style score will be based on the quality of your design.
When doing an object-oriented design, you first come up with a candidate set of classes, choosing a name for each, and identifying the responsibilities of each in the context of the larger program overall. We have done that step for you here. We are requiring you to have at least the following three classes in your solution, with the responsibilities described. You are allowed to add more classes to your design as you see fit. The three are:

GenText
This contains the main method. This class will have a main that's responsible for processing the command-line arguments, opening and closing the files, and handling any errors related to the above tasks. All the other functionality will be delegated to other object(s) created in main and their methods.
Prefix
This corresponds to the idea of a prefix in the problem description. That is, a sequence of words that you are using as a basis to choose the next word to generate. Usually this sequence consists of the prefixLength previous words you have just generated (except for when you need to get a new initial prefix -- there was more about that in the section on markov text generation). When deciding on the Prefix methods, you should think in terms of what are the things we have to do with a prefix in this program. (Hint: you can look for verbs that are associated with the word "prefix" in the problem description.)
RandomTextGenerator
This will contain most of the program logic and data structures, and will probably only have a few methods. You will create it with some form of the source text and possibly other arguments. Then it will have another method to generate random text from that source.
Although you haven't done much class design yourself, you have seen many examples of well-designed classes in the textbook, lecture, labs, and assignments in this class. We recommend you review the following sections of the textbook that give hints on deciding what classes and methods would make sense for a program design, before you start on your own design: 8.1, 8.2, 12.1, 12.2 (the last two of these were not in the original readings).
One thing to keep in mind is you want the code that operates on some data to be in the same class that contains that data. One sign that your design doesn't have that feature is if your classes tend to have a lot of get and set methods and not much else. That would indicate that all the code operating on this data is outside of the class itself.

For example, suppose you decide to use an ArrayList representation inside your Prefix class. If you provide Prefix methods such as get(i) and set(i, newvalue), that's pretty close to the ArrayList interface, so your new class isn't providing much added value over an ArrayList itself. You probably should think instead about operations that deal with the whole prefix, and how we need to access or manipulate it here. Note: your prefix class won't necessarily have an ArrayList in it -- you get to choose the representation to use.

Hopefully we've made clear the importance of making all instance variables private. But even if you make your data private there are other ways to expose the implementation of your objects. For example, if you have a class that contains an ArrayList, and also provide an accessor method for this ArrayList, it gives clients the ability to change the contents of that arraylist from outside of the object methods, possibly invalidating the object. (We discussed these types of issues in the lecture discussion of side effects in week 7.)

One more piece of advice about the Prefix class: we recommend you make it an immutable class. This can help you avoid errors related to your mistakenly changing a prefix that has multiple references to it. So, if you have a shiftIn method, it would return a new Prefix, rather than updating "this" (analogous to String's substring method).

You are welcome to (but not required to) organize your code into additional classes designed and implemented by you. Some other possible classes you might create: an exception class, if one of the existing ones don't do the job, and/or a separate class to deal with the formatting of the output. If you add classes, don't forget to submit the additional .java files. See the section on submitting your program for more about changing the submit command that's in the Makefile.


Debugging mode

To help you (and us) in figuring out if your program is doing the right thing as you develop it, and to help you diagnose bugs, we're requiring you to produce some debugging output, controlled by the "-d" command-line argument.
When debugging is turned on, you'll do two things differently:

You'll use the fixed seed, 1, for the random number generator, so the output will be predictable from one debug run to the next. When the debug flag is off, you should not use a fixed seed.
You'll print some debugging output to System.out. Each line of debugging output will start with the string "DEBUG:". When debugging is off, no output goes to the console.
More about Random and the seed. The random number sequence generated by a Random object normally depends on a seed, which is set in its constructor. The default constructor uses a different seed for every Random object you create (usually a function of the system clock), but the one-argument constructor lets the programmer set the seed. See the Java documentation for Random for more details.
Details of the required debugging output. When debugging is on, every time you generate a new word, you must print out the current prefix value, the current list of successors, and the word chosen from that list. If your program is at a point where it had to find a new random starting point (i.e., because it got to the end), it should print out a message to that effect, and the new prefix to be used. (This situation is shown as part of the example below.)

Here is an example showing part of the debugging output running on the source bluedog, whose contents were shown earlier in the section on markov text generation. In the example below the % is standing in for the Unix prompt. Note: these aren't necessarily the results you will get with a Random object with a fixed seed of 1.

% java GenText -d 2 20 bluedog bluedog.out
DEBUG: chose a new initial prefix: the big
DEBUG: prefix: the big
DEBUG: successors: blue elephant blue
DEBUG: word generated: blue
DEBUG: prefix: big blue
DEBUG: successors: dog hat
DEBUG: word generated: dog
. . .
DEBUG: prefix: his tusk.
DEBUG: successors: <END OF FILE>
DEBUG: chose a new initial prefix: hat on
DEBUG: prefix: hat on
DEBUG: successors: his 
DEBUG: word generated: his
. . .
You do not have to display prefixes in exactly the format shown here. E.g., you may print Prefix objects using the usual toString format, as long as all the Strings involved at every step are shown in the output. However, different words and word sequences printed must be labeled as above so we (and you) can easily understand the output.
When debugging is on, all the regular output gets produced as usual (e.g., to bluedog.out in this example, which would contain "blue dog . . . his . . ." along with other output).


Error checking

You will need to report and handle the following user errors. For each of the errors, once the error is reported the program should exit without generating any text.
prefixLength or numWords arguments are not integers
missing command-line arguments
numWords < 0
prefixLength < 1
input file does not exist
can't write to output file
prefixLength >= number of words in sourceFile
For the first four listed here, you should also print out a summary of how to run the program before exiting. It should look like the java command we showed near the beginning of the section called The Assignment.

Data files

If you followed the directions in the section on Assignment Files your assignment directory should contain copies of some sample data files small and large. Only once you have convinced yourself the program is working correctly by testing it on small data files with debugging turned on and checking the results, you should then try it out using of the larger sources (with debugging turned off), generating a long text sequence.

For additional real writing samples to use with this program see Project Gutenberg, which maintains a huge library of public domain books. If your program generates something that is particularly amusing, please send it to me so I can share it with the class. Be sure to identify the sample text and the prefixLength.


Grading criteria

This program will be graded approximately 70% on correctness, 30% on design, style, and documentation. As usual we will be using the style guidelines published for the class. There was more about design issues in the section on class design.

README file / Submitting your program

Your README file must document known bugs in your program, contain the signed certification shown near the top of this document, and contain any special instructions or information for the grader.

In addtion, for this assignment, the README must also document the approach you took to solving the problem (i.e., description of the data structures and algorithms involved). This was discussed in the section on approach. You will also include there information about how your class design relates to this approach, including what data structures and algorithms are encapsulated in which of your classes.

The submit rule in the Makefile shows what files you need to submit. If you add any additional files (e.g., because you add additional classes), you'll need to add them to the submit rule.

Use the following command to submit your program:

gmake submit
This requires the existence of the Makefile in your program directory. For those of you who need to edit the submit command line in the Makefile: the Makefile syntax is somewhat picky, so if you run into any problems at the very last minute, as a fall-back you can always type in your submit command at the Unix shell. But here's a hint that should solve most problems: the Makefile requires a tab (not spaces) at the start of all lines with Unix commands -- so don't erase the tab that starts the line with the Unix submit command.

