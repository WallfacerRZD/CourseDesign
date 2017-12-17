assume cs:code, ds:data

data segment
    str1 db '??????????'
    char_? db '?$'
data ends

code segment
start:  
    mov ax, data
    mov ds, ax
    ; 获取键盘输入字符串,存放在ds:dx
    mov ah, 0AH
    int 21H
    ; 获取字符长度
    mov ax, 0
    mov al, ds:[1]
    mov cx, 0h
    mov cx, al
    ;初始化指针
    mov si, 2
    mov di, 2
    call clear
    ;添加结束标志
    mov byte ptr [di], '$'
    cmp di, 2
    ; di未移动说明全是数字,显示'?'
    je show_?
    mov dx, 2
    jne exit
show_?:
    lea dx, char_?
    jmp exit
exit:
    ;显示字符串并退出
    mov ah, 9h
    int 21h
    mov ax, 4c00h
    int 21h
clear:
    mov al,[si]
    cmp al, '$'
    je copy
    cmp al, 'A'
    ; 跳过数字
    jb next1
    call captalize
copy:
    mov [di], al
    inc di
next1:
    inc si
    loop clear
    ret
captalize:
    ; 小写转大写
    and al, 11011111b
    ret
code ends
end start