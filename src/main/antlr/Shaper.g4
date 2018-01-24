grammar Shaper;

shaper      : NUM '>>>' ( row ROW_SEP)* row '<<<' ;
row       : ( shape COL_SEP )* shape ;
shape     : 'square' | 'circle' | 'triangle';

NUM       : [1-9]+ [0-9]* ;
ROW_SEP   : '|' ;
COL_SEP   : ',' ;
