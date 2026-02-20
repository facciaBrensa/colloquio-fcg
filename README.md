# Progetto colloquio Florence Consulting Group

### Consegna
Si richiede la creazione di un progetto Java che esponga le seguenti API:
- API CRUD su un'entità User composta da nome, cognome, email ed indirizzo.
- API per la ricerca degli utenti basata sui parametri di ricerca opzionali nome e cognome.
- API per la creazione massiva di utenti tramite file .csv.

L'applicazione deve inoltre soddisfare i seguenti requisiti:
- Utilizzare il sistema di versioning control Git.
- Utilizzare un'immagine docker per containerizare il software ed il relativo DB.

### Informazioni aggiuntive
Anche se non esplicitamente richiesto, il progetto include inoltre:
- Documentazione swagger tramite la libreria OpenAPI, accessibile da locale al seguente [link](localhost:8080/swagger-ui/index.html).
- Collection postman contenente degli esempi per le API implementate (/docs/colloquio-fcg.postman_collection.json) ed un esempio di file .csv (generato tramite AI generativa) da utilizzare per l'inserimento massivo (/docs/import.csv).
- Test JUnit con coverage completa del codice.
  - I test JUnit del layer controller (es. UtenteController Test) includono integration test con l'intero context di Spring + database h2.
  - I test JUnit delle altri componenti (service/parser) includono test unitari con mock.

### Istruzioni per avviare il progetto
Grazie alla containerizzazione docker con docker compose, per avviare il progetto basterà eseguire il seguente comando nella directory principale del progetto:

`docker compose up`