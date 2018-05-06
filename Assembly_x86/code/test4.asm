assume cs:code, ds:data
data segment
    VARB db 14, -25, 66, -17, 78, -33, 46, -96, 71, 80
data ends

code segment
start:
    mov ax, data
    mov ds, ax
    mov cx, 10
    mov ax, 0
    mov si, 0
s:
    mov dx, VARB[si]
    mov di, dx
    and di, 80h
    cmp di, 80h
    jne next1                ;正数直接判断
    neg dx                   ;负数取相反数后再判断
next1:   
    and dx, 01h
    cmp dx, 01h
    je add_odd_cnt          ;增加奇数计数器
    jne add_even_cnt        ;增加偶数计数器
add_odd_cnt:
    inc ah
    jmp short next2
add_even_cnt:
    inc al
next2:
    inc si
    loop s  
    mov ax, 4c00h
    int 21h
code ends
end start