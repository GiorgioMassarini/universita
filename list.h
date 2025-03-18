#ifndef _LIST_H
#define _LIST_H

#include "element.h"


typedef struct list_element {
	element value;
	struct list_element* next;
} item;

typedef item* list;
typedef int boolean;

/* PRIMITIVE */
list emptylist(void);
boolean empty(list l);
list cons(element e, list l);
element head(list l);
list tail(list l);
void showlist(list l);
void freelist(list l);
#endif
