assume cs:code, ds:data
data segment
    VAR1 db 35h
    RES1 db 00h
    RES2 db 00h
    VAR2 dd 0E2597455h
data ends
code segment
bar1:
    mov ax, data
    mov ds, ax
    mov al, VAR1
    mov RES1, al
    and RES1, 0fh                    ;保存低4位
    mov RES2, al
    and RES2, 0f0h
    mov cl, 4
    shr RES2, cl                     ;保存高4位
    ;功能一完成,开始功能二
bar2:
    mov cl, 2
    sal VAR2, cl

    mov ax, 4c00h
    int 21h
code ends
end bar1