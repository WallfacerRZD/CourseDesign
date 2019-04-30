/****************************************************/
/* File: scan.c                                     */
/* The scanner implementation for the TINY compiler */
/* Compiler Construction: Principles and Practice   */
/* Kenneth C. Louden                                */
/****************************************************/

#include "scan.h"
#include "globals.h"
#include "util.h"

/* states in scanner DFA */
typedef enum {
    START,
    INASSIGN,
    INCOMMENT,
    INNUM,
    INID,
    DONE,
    IN_EQ,
    IN_LE,
    IN_GE,
    IN_NEQ,
    LFS,
    IN_COMMENT,
    OUT_COMMENT
} StateType;

/* lexeme of identifier or reserved word */
char tokenString[MAXTOKENLEN + 1];

/* BUFLEN = length of the input buffer for
   source code lines */
#define BUFLEN 256

static char lineBuf[BUFLEN]; /* holds the current line */
static int linepos = 0;      /* current position in LineBuf */
static int bufsize = 0;      /* current size of buffer string */
static int EOF_flag = FALSE; /* corrects ungetNextChar behavior on EOF */

/* getNextChar fetches the next non-blank character
   from lineBuf, reading in a new line if lineBuf is
   exhausted */
static int getNextChar(void) {
    if (!(linepos < bufsize)) {
        lineno++;
        if (fgets(lineBuf, BUFLEN - 1, source)) {
            if (EchoSource) fprintf(listing, "%4d: %s", lineno, lineBuf);
            bufsize = strlen(lineBuf);
            linepos = 0;
            return lineBuf[linepos++];
        } else {
            fprintf(listing, "%4d: EOF\n", lineno);
            EOF_flag = TRUE;
            return EOF;
        }
    } else
        return lineBuf[linepos++];
}

/* ungetNextChar backtracks one character
   in lineBuf */
static void ungetNextChar(void) {
    if (!EOF_flag) linepos--;
}

/* lookup table of reserved words */
static struct {
    char* str;
    TokenType tok;
} reservedWords[MAXRESERVED] = {{"if", IF},     {"else", ELSE},
                                {"int", INT},   {"return", RETURN},
                                {"void", VOID}, {"while", WHILE}};

/* lookup an identifier to see if it is a reserved word */
/* uses linear search */
static TokenType reservedLookup(char* s) {
    int i;
    for (i = 0; i < MAXRESERVED; i++)
        if (!strcmp(s, reservedWords[i].str)) return reservedWords[i].tok;
    return ID;
}

/****************************************/
/* the primary function of the scanner  */
/****************************************/
/* function getToken returns the
 * next token in source file
 */
TokenType getToken(void) { /* index for storing into tokenString */
    int tokenStringIndex = 0;
    /* holds current token to be returned */
    TokenType currentToken;
    /* current state - always begins at START */
    StateType state = START;
    /* flag to indicate save to tokenString */
    int save;
    while (state != DONE) {
        int c = getNextChar();
        save = TRUE;
        switch (state) {
            case START:
                if (isdigit(c))
                    state = INNUM;
                else if (isalpha(c))
                    state = INID;
                else if (c == '=')
                    state = IN_EQ;
                else if ((c == ' ') || (c == '\t') || (c == '\n'))
                    save = FALSE;
                else if (c == '<') {
                    state = IN_LE;
                } else if (c == '>') {
                    state = IN_GE;
                } else if (c == '!') {
                    state = IN_NEQ;
                } else if (c == '/') {
                    state = LFS;
                } else {
                    state = DONE;
                    switch (c) {
                        case EOF:
                            save = FALSE;
                            currentToken = ENDFILE;
                            break;
                        case '+':
                            currentToken = PLUS;
                            break;
                        case '-':
                            currentToken = MINUS;
                            break;
                        case '*':
                            currentToken = TIMES;
                            break;
                        case '(':
                            currentToken = LPAREN;
                            break;
                        case ')':
                            currentToken = RPAREN;
                            break;
                        case '[':
                            currentToken = L_SQUARE;
                            break;
                        case ']':
                            currentToken = R_SQUARE;
                            break;
                        case '{':
                            currentToken = L_CURLY;
                            break;
                        case '}':
                            currentToken = R_CURLY;
                            break;
                        case ';':
                            currentToken = SEMI;
                            break;
                        case ',':
                            currentToken = SEMI;
                            break;
                        default:
                            currentToken = ERROR;
                            break;
                    }
                }
                break;
            case INASSIGN:
                state = DONE;
                if (c == '=')
                    currentToken = ASSIGN;
                else { /* backup in the input */
                    ungetNextChar();
                    save = FALSE;
                    currentToken = ERROR;
                }
                break;
            case INNUM:
                if (!isdigit(c)) { /* backup in the input */
                    ungetNextChar();
                    save = FALSE;
                    state = DONE;
                    currentToken = NUM;
                }
                break;
            case INID:
                if (!isalpha(c)) { /* backup in the input */
                    ungetNextChar();
                    save = FALSE;
                    state = DONE;
                    currentToken = ID;
                }
                break;
            case IN_EQ:
                state = DONE;
                if (c == '=') {
                    currentToken = EQ;
                } else {
                    ungetNextChar();
                    save = FALSE;
                    currentToken = ASSIGN;
                }
                break;
            case IN_LE:
                state = DONE;
                if (c == '=') {
                    currentToken = LT_EQ;
                } else {
                    ungetNextChar();
                    save = FALSE;
                    currentToken = LT;
                }
                break;
            case IN_GE:
                state = DONE;
                if (c == '=') {
                    currentToken = GT_EQ;
                } else {
                    ungetNextChar();
                    save = FALSE;
                    currentToken = GT;
                }
                break;
            case IN_NEQ:
                state = DONE;
                if (c == '=') {
                    currentToken = NEQ;
                } else {
                    ungetNextChar();
                    save = FALSE;
                    currentToken = ERROR;
                }
                break;
            case LFS:
                if (c == '*') {
                    state = IN_COMMENT;
                    save = FALSE;
                } else {
                    state = DONE;
                }
                break;
            case IN_COMMENT:
                save = FALSE;
                if (c == '*') {
                    state = OUT_COMMENT;
                } else {
                    // do nothing
                }
                break;
            case OUT_COMMENT:
                save = FALSE;
                if (c == '/') {
                    state = START;
                } else {
                    if (c == '*') {
                        // do nothing
                    } else {
                        state = IN_COMMENT;
                    }
                }
                break;
            case DONE:
            default: /* should never happen */
                fprintf(listing, "Scanner Bug: state= %d\n", state);
                state = DONE;
                currentToken = ERROR;
                break;
        }
        if ((save) && (tokenStringIndex <= MAXTOKENLEN))
            tokenString[tokenStringIndex++] = (char)c;
        if (state == DONE) {
            tokenString[tokenStringIndex] = '\0';
            if (currentToken == ID) currentToken = reservedLookup(tokenString);
        }
    }
    if (TraceScan) {
        if (currentToken != ENDFILE) {
            fprintf(listing, "\t%d: ", lineno);
        }
        printToken(currentToken, tokenString);
    }
    return currentToken;
} /* end getToken */
