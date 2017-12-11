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

       mov ax, 0
       mov es, ax
       mov word ptr es:[0*4], 200H
       mov word ptr es:[0*4+2], 0

       mov ax, 4c00h
       int 21h

do0: jmp short do0start
     db "overflow!"

do0start:
        mov ax, cs
        mov ds, ax
        mov si, 202h

        mov ax, 0b800h
        mov es, ax

        mov di, 12 * 160 + 36 * 2 ;设置es:di指向显存空间的中间位置

        mov cx, 9
s:      
        mov al, [si]
        mov es:[di], al
        inc si
        add di, 2
        loop s


        mov ax, 4c00h
        int 21h
do0end: nop

codeseg ends
end start