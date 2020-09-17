# IslandsPainter
Islands Counter is an app that given a dimension - creates a board in that dimension. 
It then lets you choose if you want to fill it manually or let the random algorithm do it for you. 
when the board is filled with islands, the app will efficiently paint the islands in different colors.

# IslandsCounter
This app was built after my IslandsCounter app which is very similar. In this app I used the recycler view and an adapter that enabled inserting large matrixes more than 1000 rows.

I still have a problem with large number of columns, because the adapter needs to pull the cols * cells from memory while scrolling, which make scrolling very slow for large col scale matrices.

