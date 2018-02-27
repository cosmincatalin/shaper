grammar Shaper;

shaper      : 'img_dim:' img_dim ',shp_dim:' shp_dim '>>>' ( row ROW_SEP)* row '<<<' NEWLINE* EOF;
row       : ( shape COL_SEP )* shape ;
shape     : 'square' | 'circle' | 'triangle';
img_dim   : NUM ;
shp_dim   : NUM ;

NUM       : [1-9]+ [0-9]* ;
ROW_SEP   : '|' ;
COL_SEP   : ',' ;

NEWLINE   : '\r\n' | 'r' | '\n';