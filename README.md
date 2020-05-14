# DiceParser
A parser for mathematical expressions including dice, such as "2d20+8"

## Usage
run in anyway you like with a number of arguments such as "1d6+2 2d6+4 1d20-6 -1d6 10*(1d6+4) 1d6*1d6"

## Details
This is an implementation of a recursice descent parser for the following language.

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

