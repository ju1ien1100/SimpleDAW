# Julien's Personal Project

## Accessible Audio Editing

Intentions of the project:
- Software which can be used for audio **editing** and **creation**
- The software should be comprehensible and accessible for people of all ages and abilities to use. Similar to p5.js 
for coding this software should be educational and allow users to explore audio editing. 
- A developed suite of tools for audio processing and a way for users to add/create their own tools.



For my personal project I hope to continue the intersection between music and technology
which I have been working on for the past year. For this course, I will be starting the creation
of a new software for audio editing which aims to create more accessible audio editing software
which abstracts many complex processes which users face in software's such as Abelton or FLstudios away, 
such as managing audio levels and layering audio to create a more full sound. This aims to be an educational tool
for learning how to use more advanced software and therefore should be accessible, have ease of use, and is inviting
to users. 

Given that this is an iterative process the first part will be creating the command
line tool which will be for the editing and creation of audio files. It will be able to display all
the available sounds and the user can input a custom order. This will be first for note so that they can create
measures and then, with those measures they can input the order of different ones created by the user. After making an
arbitrary amount of measures and composing them, when the user is done it will output and save the file. 
The user would have a collection of songs, which is a collection of measures, which are collections of notes/sounds. 

Once we are onto the graphical interface I will create a view for editing the measures, creating new measures, and 
adding notes to those measures. In the aims to be education it will also come with many prompts
and indicators of what should be done/ can be done. 

## User Stories
- As a user, I want to be able to play and hear audio files (yes)
- As a user, I want to be able to make different song (yes)
- As a user, I want to be able to make different tracks/measures in a song (yes)
- As a user, I want to be able to edit the tracks/measures in a song (yes)
- As a user, I want to be able to add different instruments to a song (yes)
- As a user, I want to be able to create melodies from notes (yes)
- As a user, I want to be able to create chords from notes (yes)
- As a user, I want to be able to save my work to a json file(yes)
- As a user, I want to be able to load in saved work from a json file (yes)
- As a user, I want to be able to edit a premade song (yes)
- As a user, I want to be able to save multiple songs (yes)
- As a user, I want to be able to view the different tracks/measures in my song (yes)
- As a user, I want to be able to edit the different tracks/measures (yes)
- As a user, I want to be able to control audio features with viewable inputs (yes)
- As a user, I want to be able to make multiple songs and edit them if the user chooses (yes),
this is done in the menu page and you can add and load songs into the application.
- As a user, I want to be abled to make multiple measures in a song and to add those measures in different places
this is done by pressing the new button in the navbar of the mainView header. 
- As a user, I want to be abled to add multiple notes in a measure and to add the notes to the measure
after pressing the new button, the notes should be able to have different properties. 
- As a user, I want to be abled to load in previous songs I have made in the application
- As a user, I want to be abled to save in the current song being worked on in the application. 


# Instructions for Grader
- First note that on the menu the logo is an image, I wasn't sure where else an image would go in this
- To load a previous song press the load button in the menu, this prompts you to the different songs available and 
you can pick the one that you want to work on. OR you can make a new song where it takes you to a dialogue where you can
name your new song. 
- While in the mainView you can generate "add an Xs to a Y" by pressing in the new in the top corner to make
a new measure, Additionally you can drag the given measures at the botton onto the grid and they will be added to the song.
Another feature is that you can move the measures inside of the grid to move their position in the Y
- Lastly, I know this isn't a requirement but you can also remove the Xs from the Y by double clicking on a measure in 
the grid. 
- TO add notes to a measure "adding more Xs to Y" it is pretty self explanatory and the dialogue prompts
are clear. 
- You can save the state of the application in the mainView by pressing the save button


