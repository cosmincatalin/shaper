grammar Shaper;

shaper      : dim '>>>' ( row ROW_SEP)* row '<<<' NEWLINE* EOF;
row       : ( shape COL_SEP )* shape ;
shape     : 'square' | 'circle' | 'triangle';
dim       : NUM ;

NUM       : [1-9]+ [0-9]* ;
ROW_SEP   : '|' ;
COL_SEP   : ',' ;

NEWLINE   : '\r\n' | 'r' | '\n';