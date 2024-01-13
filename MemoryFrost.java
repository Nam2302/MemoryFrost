/*
 * Date: 2024-01-17                            
 * Author: Naman Saxena
 * Description: This program is a memory, winter emoji matching game
 */

import java.util.Scanner;
import java.util.Random;
import java.lang.Math;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MemoryFrost {
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001b[34m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		Random random = new Random();

		String name, menuReturn;
		String invalidInput;

		String[] menuOptions = {ANSI_YELLOW + "Play Ranked" + ANSI_RESET, ANSI_YELLOW + "Play Casual" + ANSI_RESET, "Help", "Previous Scores", "Change Format", "Credits", "Exit"};
		String[] topNames;
		
		int[] lastScores, topScores;
		int menu, lastLevel, score, returnLength, casualLimit;
		boolean format;
		
        final int length;
        final int scoresKept;
        
        File scoreFile;
        
		//input data
		invalidInput = ANSI_RED + "Invalid Input." + ANSI_RESET;
		menuReturn = "Returning to Menu...";

        format = true;
        
        lastLevel = 0;
        menu = 0;
        scoresKept = 5;
        score = 0;
        length = menuOptions.length;
        returnLength = menuReturn.length();
        
        lastScores = new int[scoresKept];
        topScores = new int[scoresKept];
        topNames = new String[scoresKept];
        
    	scoreFile = new File("scores.txt");

		name = greeting(input);
        
		//process data
		while (menu != length) {
	        menu = displayMenu(menuOptions, input, random, invalidInput, length);
	        
	        System.out.println("You chose: " + menuOptions[menu - 1]);
	        
	        casualLimit = 50;

			if (menu == 1 || menu == 2) {
				lastLevel = inputPairs(format, random, input, invalidInput, casualLimit, menu);
				
				if (lastLevel != 0) {
		    		score = (lastLevel - 1) * 50;
		    		
		    		for (int i = scoresKept - 1; i > 0; i--) {
		    			lastScores[i] = lastScores[i - 1];
		    		}
		    		
		    		lastScores[0] = score;
		    		
                    saveScores(topScores, topNames, scoresKept, score, name, scoreFile);
		    	    
				}
				
			} else if (menu == 3) {
				displayHelp(format, random, input, invalidInput, casualLimit, menu);
				
			} else if (menu == 4) {
				previousScore(lastLevel, lastScores, scoresKept, input);
				
			} else if (menu == 5) {
				format = changeFormat(format, input, invalidInput);

			} else if (menu == 6) {
				displayCredits(input);
				
			}
			
			if (menu != length) {
			    for (int j = 0; j < returnLength; j++) {
			    	System.out.print(menuReturn.charAt(j));
		
			    	try {
			    		Thread.sleep(50);
			                
			    		if (j == returnLength - 1) {
			        		Thread.sleep(random.nextInt(500) + 100);
	
			    		}
			    	} catch (InterruptedException e) {
			    		e.printStackTrace();
			                
			    	}
			    }
			    
			    System.out.println("\n");
			}
		}
        
		//output data
		System.out.print("\nGoodbye!");
		input.close();		
	}
	

	public static String greeting(Scanner input) {
		String name;
		
		//input data
		System.out.println(ANSI_BLUE + "\t\t\t\t\t\t\t  __  __                                   _____              _   "
						 + "\n\t\t\t\t\t\t |  \\/  | ___ _ __ ___   ___  _ __ _   _  |  ___| __ ___  ___| |_"
						 + "\n\t\t\t\t\t\t | |\\/| |/ _ \\ '_ ` _ \\ / _ \\| '__| | | | | |_ | '__/ _ \\/ __| __|"
						 + "\n\t\t\t\t\t\t | |  | |  __/ | | | | | (_) | |  | |_| | |  _|| | | (_) \\__ \\ |_"
						 + "\n\t\t\t\t\t\t |_|  |_|\\___|_| |_| |_|\\___/|_|   \\__, | |_|  |_|  \\___/|___/\\__|"
						 + "\n\t\t\t\t\t\t                                   |___/\n" + ANSI_RESET);

		//process data
		System.out.print("Enter your name: ");
		name = input.nextLine();
		
        System.out.printf("\nHello %s!\n", ANSI_YELLOW + name.trim() + ANSI_RESET);

		//output data
		return (name);
	}
	
	
	public static int displayMenu(String[] menuOptions, Scanner input, Random random, String invalidInput, int length) {
		int menu;
		
        //input data
		menu = -1;
		
        for (int i = 0; i < 4; i++) {
        	if (i == 2) {
                System.out.print("Menu ");
                
            }
            
            System.out.print(displayEmojis(random) + " ");
		}
        
        for (int i = 0; i < length; i++) {
        	System.out.printf("\n  %d. %s", (i + 1), menuOptions[i]);
                
        }
        
        System.out.println();        
        
        for (int i = 0; i < 5; i++) {
            System.out.print(displayEmojis(random) + "  ");

		}

        System.out.println("\n");        

        //process data
        do {
            System.out.print("Enter the number of your choice: ");

            if (input.hasNextInt()) {
                menu = input.nextInt();

                if (menu > length || menu <= 0) {
                    System.out.printf("%s Enter a valid choice between 1 and %d.\n\n", invalidInput, length);
                }

            } else {
                System.out.printf("%s Enter a valid choice between 1 and %d.\n\n", invalidInput, length);
                input.nextLine();
            }

        } while (menu > length || menu <= 0);

		
		
		//output data
		return(menu);
	}
	

    public static String displayEmojis(Random random) {
        int index;
        String[] emojis = {"☃️", "❄️", "⛷️", "🏂", "⛄", "🌨️", "🧣", "🧤", "🎿", "🌲", "🌬️", "☕", "🔥",
                "🎄", "🎅", "🦌", "🎁", "🔔", "🍪", "🍵", "🍫", "⛸️", "❅", "🌨", "⚪", "🗻", "🛷", "🏒", "🥶", "🎇"};

    	//process data 
        index = random.nextInt(emojis.length);

        //output data
        return(emojis[index]);
    }

    public static int inputPairs(boolean format, Random random, Scanner input, String invalidInput, int casualLimit, int menu) {
    	int rowSize, level, lives;
    	int exit;
    	
    	final String heart;
    	
    	//input data
    	rowSize = -1;
    	lives = 20;
    	level = 0;
    	exit = 0;
		
        heart = ANSI_RED + "❤️" + ANSI_RESET;

    	//process data
    	if (menu == 1) {
    		level = 1;
    		
    		do {
    			System.out.printf("\nLevel %d: \n", level);
    			rowSize = (level + 1) * 2;
    			
    			lives = createCards(format, casualLimit, random, input, invalidInput, menu, rowSize, lives, heart);
    			
    			if (lives == -1) {
    				return(level);
    			}
    			    			
    			if (lives > 0) {
    				level++;

    			}
    			

    		} while (level <= casualLimit / 2 && lives > 0); 
    		
    	} else {
    		System.out.println();
    		
            do {
                input.nextLine();
	    		System.out.print("Enter the number of pairs: ");

                if (input.hasNextInt()) {
                	rowSize = input.nextInt() * 2;

                    if (rowSize > casualLimit || rowSize <= 0) {
    					System.out.printf("%s Enter a valid size between 1 and %d.\n\n", invalidInput, casualLimit / 2);
                    }

                } else {
					System.out.printf("%s Enter a valid size between 1 and %d.\n\n", invalidInput, casualLimit / 2);
                
                }


            } while (rowSize > casualLimit || rowSize <= 0 );
            	    	
			exit = createCards(format, casualLimit, random, input, invalidInput, menu, rowSize, lives, heart);
			
   			if (exit == -1) {
				return(0);
			}
    	}
    	
    	return (level);
    }
    	
    public static int createCards(boolean format, int casualLimit, Random random, Scanner input, String invalidInput, int menu, int rowSize, int lives, String heart) {
    	int randomIndex;
    	String randomEmoji;
    	boolean repeated;
    	
		String [] row;
		
		//input data
        row = new String[rowSize];

    	//process data
    	for (int i = 0; i < rowSize; i = i + 2) {
    		repeated = false;

    		randomEmoji = displayEmojis(random);

    		for (int j = 0; j < rowSize; j = j + 2) {
    			if (row[j] == randomEmoji) {
    				repeated = true;
    			}    			
    		}
    		
    		if (!repeated) {
    			row[i] = randomEmoji;
    			row[i + 1] = row[i];
    			
			} else {
				i = i - 2;
				
			}
    		
        }
 
    	for (int i = 0; i < rowSize; i++) {
    		randomIndex = random.nextInt(rowSize);
    		randomEmoji = row[randomIndex];
			row[randomIndex] = row[i];
			row[i] = randomEmoji;
			
		}
    	
    	//output data
		lives = playGame(row, rowSize, input, invalidInput, format, menu, lives, heart);
		
		return (lives);
    }
    
    public static int playGame(String[] row, int rowSize, Scanner input, String invalidInput, boolean format, int menu, int lives, String heart) {
    	boolean[] paired;
        
        boolean allPaired;
        int index1, index2, indexesNum, gridSize;
               
        //input data        
        paired = new boolean[rowSize];
        allPaired = false;
        
        gridSize = 1;
        index1 = -1;
        index2 = -1;
        indexesNum = -1;
        
        if (!format || menu == 1) {
        	gridSize = calculateGridSize(rowSize);

        }
        
        //process data
        while (!allPaired && lives > 0) {
        	if (menu == 1) {
        		System.out.print(heart + " x " + lives + "\n\n");
        	}
        	
            for (int i = 0; i < 3; i++) {
                indexesNum = i;
                displayString(rowSize, gridSize, index1, index2, paired, row, indexesNum, format, menu);

                if (i == 0) {
                    index1 = getInputIndex("first", rowSize, gridSize, input, -1, invalidInput, format, paired, menu);

                } else if (i == 1) {
                    index2 = getInputIndex("second", rowSize, gridSize, input, index1, invalidInput, format, paired, menu);
                    
                }
                
                if (index1 == -2 || index2 == -2) {
                	return(-1);
                }
                
            }
            
            System.out.print("\t\t\t\t\t\t──────────────────────── ");


            if (row[index1].equals(row[index2]) && !paired[index1] && !paired[index2]) {
                paired[index1] = true;
                paired[index2] = true;
                
                allPaired = true;
                
                for (int i = 0; i < rowSize; i++) {
                    if (!paired[i]) {
                        allPaired = false;
                        
                    } 
                }
                
                if (!allPaired) {
                	System.out.print(ANSI_YELLOW + "Pair Found! " + ANSI_RESET);
                	
                } else if (allPaired) {
                	System.out.print(ANSI_YELLOW + "String Complete!" + ANSI_RESET);

                }    
                                
            } else {
            	lives--;
            	
            	System.out.print(ANSI_RED + "No pair made! " + ANSI_RESET);
          	
            }
            
            System.out.print("────────────────────────\n\n");
                        
        }
        
        return (lives);
    }
    
    public static void saveScores(int[] highScores, String[] topNames, int scoresKept, int score, String name, File scoreFile) {
    	boolean added;
    	FileWriter writer;
    	
    	//input data
        added = false;

    	//process data
    	for (int i = 0; i < scoresKept && !added; i++) {
	        if (score > highScores[i]) {
	            for (int j = scoresKept - 1; j > i; j--) {
	                highScores[j] = highScores[j - 1];
	                
	            }
	            
	            highScores[i] = score;
	            topNames[i]  = name;
	            
	            added = true;
	        }
	    }
    	
    	try {
            writer = new FileWriter(scoreFile);

            for (int i = 0; i < highScores.length; i++) {
                writer.write(highScores[i] + " " + topNames[i] + "\n");
            }

            writer.close();
            
        } catch (IOException e) {
            System.out.println("Error saving scores to file");
            
        }

    }
    
    public static int getInputIndex(String place, int rowSize, int gridSize, Scanner input, int previousIndex, String invalidInput, boolean format, boolean[] paired, int menu) {
        int index, row, column;
        String exit, message;

        //input data
        index = -1;
    	message = "Are you sure you want to exit? (yes/no): ";

        //process data
        while (index < 0 || index >= rowSize || index == previousIndex || paired[index]) {
        	if (format && menu != 1) {
                System.out.print("Enter " + place + " index: ");
                if (input.hasNextInt()) {
                    index = input.nextInt();

                    if (index < 0 || index >= rowSize) {
                        System.out.printf("%s Enter a number between 0 and %d.\n", invalidInput, rowSize - 1);

                    } else if (index == previousIndex || paired[index]) {
                        System.out.printf("%s Emoji already displayed.\n", invalidInput);

                    }
                	
                } else {
                    exit = input.next().trim().toLowerCase();

                    if (exit.equals("exit") || exit.equals("e")) {
                    	System.out.println();
                    	                    	
                    	exit = validateInput(message, input, invalidInput);
                    	                    	
                    	if (exit.equals("yes") || exit.equals("y")) {
                    		return(-2);
                    		
                    	} else {
                    		System.out.println("Continuing game...");
                    		
                    	}

                    } else {    
                        System.out.printf("%s Enter a number between 0 and %d.\n", invalidInput, rowSize - 1);

                    }
                }

            } else {
                System.out.print("Enter " + place + " row: ");
                if (input.hasNextInt()) {
                    row = input.nextInt();

                    if (row < 0 || row >= rowSize / gridSize) {
                        System.out.printf("%s Enter a valid row between 0 and %d.\n", invalidInput, rowSize / gridSize - 1);

                    } else {
                        System.out.print("Enter " + place + " column: ");

                        if (input.hasNextInt()) {
                            column = input.nextInt();

                            if (column < 0 || column >= gridSize) {
                                System.out.printf("%s Enter a valid column between 0 and %d.\n", invalidInput, gridSize - 1);

                            } else {
                                index = row * gridSize + column;

                                if (index == previousIndex || paired[index]) {
                                    System.out.printf("%s Emoji already displayed.\n", invalidInput);

                                }
                            }
                            
                        } else {
                        	exit = input.next().trim().toLowerCase();

                             if (exit.equals("exit") || exit.equals("e")) {
                             	System.out.println();
                             	                    
                             	exit = validateInput(message, input, invalidInput);
                             	                    	
                             	if (exit.equals("yes") || exit.equals("y")) {
                             		return(-2);
                             		
                             	} else {
                             		System.out.println("Continuing game...");
                             		
                             	}

                             } else {    
                                 System.out.printf("%s Enter a valid column between 0 and %d.\n", invalidInput, gridSize - 1);

                             }
                            
                        }
                        
                    }
                    
                } else {
                	exit = input.next().trim().toLowerCase();

                    if (exit.equals("exit") || exit.equals("e")) {
                    	System.out.println();
                    	                    
                    	exit = validateInput(message, input, invalidInput);
                    	                    	
                    	if (exit.equals("yes") || exit.equals("y")) {
                    		return(-2);
                    		
                    	} else {
                    		System.out.println("Continuing game...");
                    		
                    	}

                    } else {    
                        System.out.printf("%s Enter a valid row between 0 and %d.\n", invalidInput, rowSize / gridSize - 1);

                    }
                    
                }
                
            }
                        
            System.out.println();
            input.nextLine();

        }

        //output data
        return (index);
    }
    
    public static int calculateGridSize(int rowSize) {
        int gridSize, minimumSum, factor1, factor2, sum;

        //input data
        gridSize = 1;
        minimumSum = rowSize + 1;
        
        //process data
        for (int i = 1; i <= Math.sqrt(rowSize); i++) {
            if (rowSize % i == 0) {
                factor1 = i;
                factor2 = rowSize / i;
                
                sum = factor1 + factor2;

                if (sum < minimumSum) {
                	minimumSum = sum;
                    gridSize = factor1;
                    
                }
            }
        }

        //output data
        return (gridSize);
    }

    public static void displayString(int rowSize, int gridSize, int index1, int index2, boolean[] paired, String[] row, int indexesNum, boolean format, int menu) {
        final char divider;
        String space;
    	
        //input data
        divider = '-';
	    System.out.print("String: ");

	    //process data
        if (!format || menu == 1) {
            space = "\t";
            System.out.println();
            
        } else {
        	space = " ";
        	
        }
        
    	//output data 
	    for (int i = 0; i < rowSize; i++) {
	    	if ((indexesNum == 0 && !paired[i]) || (indexesNum == 1 && i != index1 && !paired[i]) || (indexesNum == 2 && i != index1 && i != index2 && !paired[i])) {
	    		System.out.print(divider + space);
	                
	    	} else {
	    		System.out.print(row[i] + space);
	    		
	    	}
	    	
	    	if ((!format || menu == 1) && (i + 1) % gridSize == 0) {
                System.out.println(); 
                
            }
	    }
	    	    	
        System.out.println("\n");

    }
    
    
    public static void displayHelp(boolean format, Random random, Scanner input, String invalidInput, int casualLimit, int menu) {
    	boolean validation;
    	String demo, message;
    	
    	//input data
    	demo = "";
    	validation = false;
    	message = "Would you like a demo? (yes/no): ";
    	
    	//output data
    	System.out.println("Memory Munch is a Food and Drink-based memory game.\n");
    	
    	System.out.println(ANSI_YELLOW + "Rules:" + ANSI_RESET + "\n  "
    			+ "1. You are shown a series of dashed strings which represent an unmatched emoji\n  "
    			+ "2. Until all pairs are completed, pick two indexes and are attempting to pair the located emojis\n");
    	
    	System.out.println(ANSI_YELLOW + "Casual Games:" + ANSI_RESET + "\n  "
    			+ "1. Ability to choose between Row and Grid Format\n  "
    			+ "2. Ability to choose length of String\n");
    	
    	System.out.println(ANSI_YELLOW + "Ranked Games:" + ANSI_RESET + "\n  "
    			+ "1. 25 Levels, with increased difficulties\n  "
    			+ "2. Only Grid Format\n  "
    			+ "3. Lives System set to your total score\n  ");
    	
    	System.out.println(ANSI_YELLOW + "Index Location:" + ANSI_RESET + "\n  "
    			+ "  Row -> - - - - - - - - - -\n  "
    			+ "Index -> 0 1 2 3 4 5 6 7 8 9\n\n"
    			+ "Grid ->  -\t-\n\t -\t-\n\t -\t-\n\t -\t-\n\n"
    			+ "Index -> 00\t01\n\t 10\t11\n\t 20\t21\n\t 30\t31\n\n\t"
    			+ "*Left digit -> Row\n\t*Right digit -> Column\n");
    	
    	demo = validateInput(message, input, invalidInput);
    	
    	if (demo.equals("yes") || demo.equals("y")) {
    		do {
            	System.out.print("How would you like it displayed? (row/grid): ");
            	demo = input.next().toLowerCase().trim();
            	
            	validation = demo.equals("row") || demo.equals("grid") || demo.equals("r") || demo.equals("g");

            	if (!validation) {
    				System.out.printf("%s Enter row or grid.\n\n", invalidInput);
    				
            	}
            	
    		} while (!validation);
    		
    		if (demo.equals("row") || demo.equals("r")) {
    			inputPairs(true, random, input, invalidInput, casualLimit, menu);
			
    		} else {
    			inputPairs(false, random, input, invalidInput, casualLimit, menu);
    		
    		}
    		
    	} else {
    		System.out.println();
    		
    	}
    	
    }
    
    public static void previousScore(int level, int[] lastScores, int scoresKept, Scanner input) {
    	if (level == 0) {
    		System.out.println("\nPlay a ranked game to see your score!");
    		
    	} else {
    		//output data
    		System.out.println(ANSI_YELLOW + "Previous 5 Scores:" + ANSI_RESET);
    		
    		for (int i = 0; i < scoresKept; i++) {
    			System.out.printf("  %d. %d\n", (i + 1), lastScores[i]);
    		}
    		
    		System.out.println("");
    		
    	}
    	
    	System.out.print("\nPress Enter to return to menu: ");
    	input.nextLine();
    	
    	while (!input.hasNextLine()) {
    		
    	}
    	
    	input.nextLine();

    }    

    public static boolean changeFormat(boolean format, Scanner input, String invalidInput) {
    	String changeFormat, message;
    	
    	//input data
    	changeFormat = "";
    	message = "Would you like to change it? (yes/no): ";
    	
    	System.out.print("The current format is ");
    	
    	if (format) {
    		System.out.println("Row.\n");
    		
    	} else {
    		System.out.println("Grid.\n");
    		
    	}
    	
    	//process data
    	changeFormat = validateInput(message, input, invalidInput);
    	
    	//output data
    	if (changeFormat.equals("yes") || changeFormat.equals("y")) {
    		format = !format;
    		
    		System.out.print("Casual mode format updated to ");
    		if (format) {
    			System.out.print("Row!");
    			
    		} else {
    			System.out.print("Grid!");
    		}
    		
    		System.out.println("\n");
		}
    	
    	return (format);
    }
    
    public static String validateInput(String message, Scanner input, String invalidInput) {
    	String userInput;
    	boolean validation;
    	
    	//input data
    	userInput = "";
    	validation = false;
    	
    	input.nextLine();
    	
    	//process data
    	while (!validation) {
        	System.out.print(message);
        	userInput = input.next().toLowerCase().trim();
        	
        	validation = userInput.equals("yes") || userInput.equals("no") || userInput.equals("y") || userInput.equals("n");

        	if (!validation) {
				System.out.printf("\n%s Enter a yes or no.\n\n", invalidInput);
				
        	}
    	}
    	
    	//output data
    	return (userInput);

    }
    
    public static void displayCredits(Scanner input) {    	    	
    	//output data
    	System.out.println(ANSI_YELLOW + "\nGame Director/Programmer:" + ANSI_RESET 
    								   + "\n• Naman Saxena");
    	
    	System.out.println(ANSI_YELLOW + "\nThank you to: " + ANSI_RESET
    								   + "\n• WinterHacks"
    								   + "\n• mixkit.co");
    	
    	System.out.print("\nPress Enter to return to menu: ");
    	input.nextLine();
    	
    	while (!input.hasNextLine()) {
    		
    	}
    	
    	input.nextLine();
	}
    
}