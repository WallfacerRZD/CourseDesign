;内中断
assume cs: codeseg
codeseg segment
start: mov ax, cs
       mov ds, ax
       mov ax, 0H
       mov es, ax
       mov di, 200H
       mov si, offset do0
       mov cx, offset do0end - offset do0

       cld
       rep movsb

       ;设置中断向量表

       mov ax, 4c00h
       int 21h




do0:
        ;中断处理程序
        ;显示字符串'overflow'
        mov ax, 4c00h
        int 21h
do0end: nop

codeseg ends
end start