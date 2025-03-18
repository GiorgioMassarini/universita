#ifndef _HR_H
#define _HR_H

#include "list.h"

list leggi_posizioni(char* fileName);
Candidato* leggi_candidati(FILE* file, int* dim);
Candidato* trova_candidati(char* p_id, list p, Candidato* c, int dim_c, int* dim);
#endif
