# workoutTracker by Josefin Rahm
*Note - The project is currently being adjusted so that the user can choose file to open and save in dialog 
instead of changing the code, there may be som bugs when saving workout*

## Introduction
Since I like to lift weights, I decided to make this program to practice JavaFX and data collections for the 
course PROG2 @ Stockholm University. This is a hobby project and has not been tested by anyone else than me. 
The program was first customized to only work on my computer with a predetermined text document, and I have 
not spent that much time on adjusting the code since syncing the repository with github. Other concepts in the 
code may also be unfinished/unnecessary, since the main focus for me in this project was practising basic concepts 
of Java without introducing too many libraries.

## How to use
The program starts by reading a textfile choosen by the user with lifts. The display will be empty at first.
More exercises can be added by the user in the class Exercise, will probably add a feature for adding this 
when the program is running later on.

#### New
Creates a new lift. Exercise needs to be choosen in the right panel. Fill in sets, reps, weight and date 
(yyyy-mm-dd) in the pop-up.

#### Show
Shows all lift regardless of exercise choosen in the right panel.

#### Calculate Total
Calculates powerlifting total; squat+bench press+deadlift.

#### Save
Saves workouts to choosen text file.

#### Show all lifts
Shows all lifts for choosen exercise.

#### Show best lift
Shows best lift for choosen exercise.

#### Calculate 1RM
Calculates one rep max for choosen exercise.

#### Loading text file
The lifts in text file needs to have the format date,exercise,sets,reps,weight to be read by the program.

Example: 2020-7-6,Squat,1,5,70.0
