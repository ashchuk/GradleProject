package com.ashchuk.ashchuksjavalibrary;

import java.util.Random;

// Jokes from http://iteslj.org/c/jokes-short.html

public class JokerClass {

    public static void main(String[] args) {
        System.out.println(getJoke());
    }

    private static final String[] GAE_JOKES = {
            "GAE\n" +
                    "A: I have the perfect son.\n" +
                    "B: Does he smoke?\n" +
                    "A: No, he doesn't.\n" +
                    "B: Does he drink whiskey?\n" +
                    "A: No, he doesn't.\n" +
                    "B: Does he ever come home late?\n" +
                    "A: No, he doesn't.\n" +
                    "B: I guess you really do have the perfect son. How old is he?\n" +
                    "A: He will be six months old next Wednesday.\n",

            "GAE\n" +
                    "Girl: You would be a good dancer except for two things.\n" +
                    "Boy: What are the two things?\n" +
                    "Girl: Your feet.\n",

            "GAE\n" +
                    "Patient: Doctor, I have a pain in my eye whenever I drink tea.\n" +
                    "Doctor: Take the spoon out of the mug before you drink.",

            "GAE\n" +
                    "A: Just look at that young person with the short hair and blue jeans. Is it a boy or a girl?\n" +
                    "B: It's a girl. She's my daughter.\n" +
                    "A: Oh, I'm sorry, sir. I didn't know that you were her father.\n" +
                    "B: I'm not. I'm her mother.\n"
    };

    private static final String[] JOKES = {
            "A: Why are you crying?\n" +
                    "B: The elephant is dead.\n" +
                    "A: Was he your pet?\n" +
                    "B: No, but I'm the one who must dig his grave\n",

            "A: Hey, man! Please call me a taxi.\n" +
                    "B: Yes, sir. You are a taxi.\n",

            "Little Johnny: Teacher, can I go to the bathroom?\n" +
                    "Teacher: Little Johnny, MAY I go to the bathroom?\n" +
                    "Little Johnny: But I asked first!\n",

            "Two goldfish in a bowl talking:\n" +
                    "Goldfish 1: Do you believe in God?\n" +
                    "Goldfish 2: Of course, I do! Who do you think changes the water?\n"
    };

    public static String getJoke() {
        return JOKES[new Random(System.currentTimeMillis()).nextInt(3)];
    }

    public static String getGAEJoke() {
        return GAE_JOKES[new Random(System.currentTimeMillis()).nextInt(3)];
    }


}