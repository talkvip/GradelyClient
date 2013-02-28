Gradely Client
=============

This is the Client for the Dropbox-like homework turn in system Gradely.

Gradely is a system designed for classrooms to manage the turn-in, and grading of homework and assignments. The goal is to make Gradely as easy-to-use and as pain-free as possible. There will be no better way of turning in assignments electronically. 

How It Works
-------------

All a student has to do to turn in an assignment electronically is save the file to a automatically created folder. The file is then seamlessly uploaded to the school's Gradely server as well as the teacher's computer. The Teacher can the grade the assignment directly through Gradely, give comments, and even modify student's files (for example, to write electronic notes on) while preserving the student's original work. The Student will then automatically receive an email detailing the teacher's notes and grades.


Tech Specs
-------------

There are two components to Gradely: The Client, which is installed on the teacher's and student's computers, and the Server which runs on a server hosted by the school. The student’s files can eithr be stored on the same server Gradely runs on, or on an entirely different server. Gradely coordinates when files are uploaded at any time. The raw storage for the server can take the form of:

* FTP 
* SFTP
* Amazon S3 (Reduced Redundany Storage is preferred for cost reasons)
* WEBDAV
* Google Storage
* Rackspace Storage
* SAMBA 

Written entirely in Java, Gradely has the potential to run on almost any platform. Windows, OSX, and Linux versions are planned. File manager integration for Windows is currently only planned. In the future we hope to support OSX's Finder and GNOME's Nautilus.

