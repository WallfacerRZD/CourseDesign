assume cs:code

data segment
    db 'Welcome to masm!', 0
data ends

; code segment
; start:
;     mov dh, 8
;     mov dl, 3
;     mov cl, 2
;     mov ax, data
;     mov ds, ax
;     mov si, 0
;     call show_str

;     mov ax, 4c00h
;     int 21h

; show_str:

; code ends
; end start



; assume cs:code,ds:data,ss:stack  
  
; data segment  
;     db 'welcome to masm!',0  
; data ends  
  
; stack segment  
;     dw 8 dup (0)  
; stack ends  
  
; code segment  
; start:  mov dh,8  
;     mov dl,3      ;第八行,第三列  
  
;     mov cl,3      ;颜色  
  
;     mov ax,data  
;     mov ds,ax     ;ds指向数据段  
  
;     mov si,0  
;     call show_str  
  
;     mov ax,4c00h  
;     int 21h  
  
; show_str:  
;     push dx  
;     push cx  
;     push ax  
;     push ss  
;     push si    ;子程序开始所有寄存器入栈  
  
;     mov ax,0B800h  
;     mov es,ax    ;es指向显示的起始地址  
  
; ;计算行偏移量  
;     mov al,0a0h ;160个字节一行  
;     dec dh      ;行号减一,00 - dh-1  共dh行  
;     mul dh      ;相乘  结果放在ax里  
;     mov bx,ax   ;偏移量  
      
;     mov al,2   ;一列两个字符  
;     dec dl      ;列号减一,00 -dl-1 共dl列  
;     mul al  
;     add bx,ax  ;偏移地址计算完成  
  
;     mov di,0     ;di字符读取时候每次的偏移 每次加1  
;     mov si,0  
      
;     mov al,cl   ;颜色暂时保存在al里 线面cx需要用到  
; s1:   
;     mov ch,0    ;置0  
;     mov cl,ds:[di]  ;字符放在cl里  
;     jcxz ok  
;     mov ch,al     
;     mov es:[bx+si],cx   ;字符颜色一起放入显示位置  
  
;     add si,2  
;     inc di  
; jmp short s1  
; ok:   
;     pop si    ;子程序开始所有寄存器入栈  
;     pop ss  
;     pop ax  
;     pop cx  
;     pop dx  
;     ret  
  
; code ends  
; end start 

assume cs:code, ds:data
data segment
    string db 'hello, world!$'
data ends

code segment
start:
    mov ax, data
    mov ds, ax
    lea dx, string
    mov ah, 09h
    int 21h
    mov ax, 4c00h
    int 21h
code ends

end start

