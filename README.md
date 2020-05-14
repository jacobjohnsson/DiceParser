# DiceParser
A parser for mathematical expressions including dice, such as "2d20+8"

### Usage
Run in anyway you like with a number of arguments seperated by spaces, such as "1d6+2 2d6+4 1d20-6 -1d6 10*(1d6+4) 1d6*1d6". This could produce the output  
1d6+2: 5  
2d6+4: 9  
1d20-6: 11  
-1d6: -2  
10*(1d6+4): 100  
1d6*1d6: 12  

### Details
This is an implementation of a recursice descent parser for the LL(1) language below which can and is done predictively in in linear time. The Shunting yard algorithm is used to build the abstract syntax tree for later evaluation.

Expr  --> Par {BinOp Par}  
Par   --> "(" Expr ")"  
Par   --> UnOp Par  
Par   --> leaf  
BinOp --> "+"  
BinOp --> "-"  
BinOp --> "*"  
BinOp --> "/"  
UnOp  --> "-"  

leaf  --> Num | Dice  
Dice  --> Num "d" Num  
Num   --> "0" | "1" | ... 

