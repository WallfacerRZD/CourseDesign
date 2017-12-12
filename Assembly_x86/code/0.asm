;内中断

assume cs:code, ds:data

data segment
        string db 'divide error!$'
data ends

code segment
start:
        ;设置中断处理程序
        mov ax, 0
        mov es, ax
        mov ax, cs
        mov ds, ax

        mov di, 0200H
        mov si, offset show_str
        mov cx, offset show_end - offset show_str
        cld
        rep movsb       ;ds:si的字节搬到es:di

        ;设置中断向量表
        mov ax, 0
        mov es, ax
        mov word ptr es:[0*4], 200h
        mov word ptr es:[0*4+2], 0

        ;执行除法
        mov ax, 1000h
        mov bh, 1
        div bh
        mov ax, 4c00h
        int 21h

show_str:
        mov ax, data
        mov ds, ax
        lea dx, string
        mov ax, 0900h
        int 21h
        mov ax, 4c00h
        int 21h
show_end:
        nop        
code ends
end start