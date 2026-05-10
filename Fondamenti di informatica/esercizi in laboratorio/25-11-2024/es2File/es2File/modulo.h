typedef struct {
	unsigned int matr;
	unsigned int CDL;
} Dati;

typedef struct {
	unsigned int matr;
	char nome[21];
	char cognome[31];
	char via[31];
	char citta[31];
	unsigned int CAP;
} Indirizzo;

typedef struct {
	unsigned int matr;
	char nome[21];
	char cognome[31];
	char via[31];
	char citta[31];
	unsigned int CAP;
	unsigned int CDL;
} Elemento;

typedef Elemento Tabella[10];

Elemento riempiel(Dati d, Indirizzo i);