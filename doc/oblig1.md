# Rapport – innlevering 1

## Oppgave A0: Organisering

- Gruppenavn: _Karan med varan_
- Discord-server er laget.
- Ifht. bakgrunn går alle datateknologi og har tatt mye av de samme fagene. De mest relevante fagene for systemutvikling er INF100, INF101, INF102, INF122, INF113. Jonas har jobbet som utvikler, dog ikke innen spill, men stiller med erfaring fra prosjektmetodikk og utvikling. Kristian har verv innen webkom som gir han erfaring med git og teamarbeid.

**Team-medlemmer:**

- Kristian Elde Johansen
- Lauritz Angeltveit
- Mathias Hop Ness
- Vetle Larsen
- Jonas Bergaas
- Sune Eriksson Lianes

## Oppgave A2: Konsept

Kort og enkelt er konseptet et "fighting" spill der to spillere kjemper mot hverandre til en av spillerne dør. Spillet er i klassisk 2D-fighter stil, og minner om gamle storheter som Tekken og Street-fighter.

#### Figurer og kontroller

- To karakterer der den ene av dem er styrt av en spiller, mens den andre styres av AI.
- Kontroller for å bevege karakteren til høyre/venstre og hopping, samt slag, spark og blokkering.

#### Todimensjonal verden

- Verden skal bestå av plattform(er) der hensikten er at man kan gå, stå og hoppe på disse.
- Spillerne beveger seg på de synlige plattformene. Dersom man går utenfor disse vil man falle ned og dø.
- Verdenen er like stor som skjermen. Altså man ser hele verdenen hele tiden.
- Dersom man beveger seg utenfor skjermen dør man. Dette vil enten forhindres av vegger, eller gjøres tydelig ved at man faller i vann/lava eller ned fra en stor høyde.

#### "Fighting"

- Hensikten med spillet er å bekjempe motstanderen.
- Det finnes HP (healthpoints) for hver av spillerne. Når en spillers HP går til 0, taper den spilleren "fighten" og den andre vinner spillet.

#### Interaksjoner

- Det skal være mulig å bevege seg med høyre og venstre piltast.
- Karakteren skal kunne hoppe med piltast opp.
- Funksjonalitet for å skade motstanderen ved slag og spark, i tillegg til mulighet til å blokkere for å unngå å ta skade (det er foreløpig ikke bestemt hvilken tast som skal kontrollere disse).

#### Utfordringer

- Bevege seg på plattformen(e) uten å dette ut.
- Bekjempe motspilleren før den bekjemper deg.

#### Eventuelle forbedringer

- Diverse hindringer? Vegger man må hoppe over på plattformene? Flyvende objekter man må holde seg unna for å ikke miste liv?
- Spillerne kan samle opp "power-ups" som kan gi dobbel løpehastighet, dobbelthopp, midlertidig udødelig.
- Plattformer som beveger seg ila. kampen.
- Ulike levler som ser noe annerledes ut, plattformene og hindringene kan se annerledes ut.
- 2 player, altså ingen AI.
- Forskjellige interaksjoner (spark, slag, kombinasjoner, etc.).

## Oppgave A3: Prosesser for teamet

### Møter og hyppighet av dem

- Møter blir arrangert hver torsdag fra klokken 14:15 - 16:00 i booket grupperom (209M1) på høytek.
- Ved behov vil vi arrangere ekstra møter på tidspunkter vi blir enig om felles.

### Kommunikasjon mellom møter

- Vil hovedsaklig foregå på discord serveren.

### Arbeidsfordeling (Endring kan forekomme)

- Alle er utviklere

- Sune: Møte ansvarlig
- Jonas: Trello ansvarlig
- Lauritz: Lyd, grafikk ansvarlig
- Kristian: Test ansvarlig
- Vetle: Struktur ansvarlig
- Mathias: Kodestil ansvarlig

### Oppfølging av arbeid

- Trello brukes aktivt for å logge, systematisere og kontrollere arbeid.

### Regler for git

- Om man skal redigere eller legge til features, så skal man opprette en egen branch kun for den ene featuren. Branchen må ha et beskrivende navn på det du skal utføre.
- Når man commiter, så skal man ha en god beskrivelse på hva du har gjort.
- Når du skal merge, så må en annen reviewe merge requesten din, altså ikke godkjenn requesten selv.
- Etter merge er gjennomført, så skal branchen fjernes.

## Oppgave A3: Oversikt over MVP

### kort beskrivelse av det overordnede målet for applikasjonen

- Målet for MVP er å lage et funksjonelt spill med grunnleggende features. Spillet på dette stadiget må ha oversiktlig struktur og god dokumentasjon slik at det blir enkelt å legge til forbedringer og videreutvikle spillet.

### Krav til Minimum Viable Product (MVP)

1. Vise enkelt spillebrett
2. Vise spiller og motspiller
3. Flytte spiller
4. Hoppe, enkel fysikk/gravity, interagere med terreng
5. Spiller kan slå
6. Spiller kan dø, falle av plattform på brettet
7. Spiller kan ta skade
8. Start-skjerm/"winner" skjerm, enkel instruks skjerm

### Priosritert liste over brukerhistorier med tilhørende akseptansekrav

1. Som spiller må jeg kunne skille mellom de to karakterene på brettet slik at jeg kan vite hvilken spiller jeg kontrollerer. For å oppfylle dette kravet, må karakterene ha forskjellige farger og/eller utsende.

MVP-krav som inngår i brukerhistorie:

- 2. Vise spiller og motspiller
- 3. Flytte spiller
- 4. Hoppe, enkel fysikk/gravity, interagere med terreng

---

2. Som spiller trenger jeg instrukser for hvordan jeg skal bevege spiller og interagere med spillebrettet for å vite hvordan jeg skal spille spillet. For å oppfylle dette kravet må vi implementere visuelle instrukser på skjermen som forklarer hvilke taster man skal bruke for å gjøre ulike handlinger og instruksjoner om hva som er målet med spillet.

MVP-krav som inngår i brukerhistorie:

- 8. Start-skjerm/"winner" skjerm, enkel instruks skjerm

---

3. Som spiller er det viktig at jeg får visuell bekreftelse når de to spillkarakterene interagerer med hverandre i form av slag/ta skade. For å oppfylle dette kravet trenger vi å implementere en form for animasjon ved vellykkede slag og/eller en form for HUD med en "healthbar" som tilhører hver av de to spillerene.

MVP-krav som inngår i brukerhistorie:

- 5. spiller kan slå
- 7. Spiller kan ta skade

---

4. Som utvikler/tester er det viktig at at alle som skriver kode følger grunnleggende prinsipper som gjør det enkelt for forskjellige utviklere å lese og gjøre endringer på koden. For å oppfylle dette kravet må metoder og klasser være dokumentert og det blir brukt beskrivende navn på variabler og metoder, koden må også overholde regler om abstraksjon/innkapsulering og følge model-view-controller strukturen.

---

5. Som Utvikler trenger jeg å kunne skille objekter, platformer og bakgrunn på spillbrettet for å kunne bestemme reglene for bevegelsesmønsteret til karakterene når de interagerer med de "fysiske" objektene på skjermen. For å oppfylle dette kravet er det viktig at en skriver kode som følger objektorienterte prinsipper. Det er også viktig at det visuelle på skjermen har tydelige skiller mellom hva som er hva.

MVP-krav som inngår i brukerhistorie:

- 1. Vise enkelt spillebrett
- 2. Vise spiller og motspiller
- 3. Flytte spiller
- 4. Hoppe, enkel fysikk/gravity, interagere med terreng

---

6. Som spiller, ønsker jeg å vite når karakteren min er dø og når spillet starter eller er ferdig.
   For å oppfylle dette kravet er det viktig at når en av spillerne går tom for liv eller faller utenfor plattformen skal det komme en visuell bekreftelse på skjermen som gjør det tydelig at spillet er ferdig og hvem som har vunnet "kampen". Disse bekreftelsene vil kommer i form av "Start"- og "Winner/Gameover"- skjermer.

MVP-krav som inngår i brukerhistorie:

- 6. Spiller kan dø, falle av plattform på brettet
- 8. Start-skjerm/"winner" skjerm, enkel instruks skjerm

## Oppgave A4: Kode

- Prosjektoppsettet kompilerer og viser krokodillen på skjermen for alle.
- Vi har prøvd litt parprogrammering og programmering hver for oss, for å teste ut rammeverket.
- Startet med å følge guiden på libdgx-siden (https://libgdx.com/wiki/start/a-simple-game) for å lage et enkelt spill.
- Lagt til andre bilder og lyder for å sjekke hvordan det fungerer.
- Lagt til kontroller ved mus og keyboard.
- Dette er gjort i egne mapper, har dermed ikke endret noe av koden i selve repoet.

## Oppgave A5: Retro

#### Hva har gått bra?

- Vi har jobbet effektivt under møtene, og alle har bidratt godt.
- Jevnlig god komunikasjon på discord. Dette har gjort det enkelt for alle å holde seg oppdatert på hvordan vi ligger an.
- Planleggingen av prosjektet har gått bra. Vi har kommet opp med et konsept som vi synes er spennende, og som har potensial til å bli skikkelig kult! Vi har stor enighet og entusiasme for konseptet vi landet på!
- Oppsett av repo gikk relativt smertefritt.
- Vi har blitt enige om å bruke trello for å holde oversikt over arbeidsoppgaver som må gjøres, hva som jobbes med og hva som har blitt gjort.
- Alle begynner å få en grunnleggende forståelse for git. Dette var noe vi så på som et hinder helt i oppstartsfasen.
- Alle er flinke til å hjelpe hverandre, og vi har en god tone innad i gruppa.

#### Hva har fungert dårllig eller ikke som forventet?

- Fått noen merge-conflicts som vi har måttet lære oss å håndtere.
- Mye forarbeid før vi kunne komme i gang med kodingen (Planlegging av konsept, MVP, rollefordeling, etc.)
- Git-repoet vårt passerer ikke 'pipelinen' i GitLab. Dette er noe vi må finne ut av i fremtiden, og finne en mer permantent løsning på enn bare å tvinge gjennom merge-requests.
- Vanskelig å beregne tidsbruk på ulike oppgaver. Ofte tar ting lenger tid enn først antatt.
- Vi greide ikke å opprette en gruppe på GitLab. Derfor endte vi opp med å lage repoet direkte under en av medlemmenes workspace.

#### Hva vil vi teste ut fremover?

- Vi vil prøve å lage et UML-diagram for å visualisere strukturen til prosjektet. Frem til nå har vi snakket mest om hvordan vi vil at spillet skal se ut for brukeren. Neste steg blir å lage en oversikt over kode-strukturen.
- Teste ut forskjellige stiler på UI/UX-design.
- Vi vil fortsette med å være flinke til å holde trello-boardet oppdatert. Dette vil nok bli mer utfordrende utover i prosjektet når arbeidsoppgavene blir flere og mer omfattende, men det er noe vi tror vi vil få mye igjen for.
- Vi vil prøve å bruke møtene til å delegere arbeidsoppgaver slik at vi kan være mer frie til å jobbe på egenhånd uten at vi ødelegger for hverandre.
