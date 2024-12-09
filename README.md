# COS225_FinalProject

Collaborators:
-Garrett Rumery (0xRum)
-Kursten Massey (Ms-Walsbee)
-Siaka Diarra (sia)

Classes: DatabaseManager, Document, DocumentUploader, Menu, Summarizer, & TextProcessor

Resources: Stack Overflow, VJ's MovieApp, MongoDB

Description: LegalDocSummarizer is a Java application designed to facilitate the creation of document summaries. Utilizing MongoDB, the application enables the creation of a centralized database to store documents along with key metadata, including titles, authors, and categories. A variety of user-friendly features have been implemented, allowing users to easily request document summaries based on different search criteria. Users can search for documents by title, author, or category, enhancing the accessibility and organization of document information.

Purpose: Time is a valuable resource, and LegalDocSummarizer is designed to help users maximize it. By providing an efficient way to generate document summaries, the application enables users to quickly access key information without the need to read through entire documents, saving both time and effort.

How to use: To use, the user will be able to choose one of the eight different options that are available in our Menu.java file. Unless the user clicks option eight, the application will continue to provide the menu after completing the previous option selected by the user. For options one through seven, the application will allow for the user to sumbit a new document manually, request a summary of a document that has successfully been added to the database collection, search the database collection via author or title, and finally display attributes of the documents submitted by the user. To exit the application the user will need to select option eight on the menu. 