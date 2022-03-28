# Network-Programming-Onion-Routing-Project

Dette prosjektet er et forsøk på å implementere Onion Routing. Ut i fra min forståelse gjøres Onion routing delen av nodene, encrypt og decrypt. Fremfor at brukeren/klienten aksesserer en webside direkte, tar han veien gjennom nodene (hvor selve informasjonen/dataen er kamuflert) før han kommer til serveren som gjør den faktiske GET requesten. Responsen derfra blir så sendt tilbake til klienten som etterspurte requesten. Dette tillater en bruker å fysisk være på et annet sted enn der requesten blir eksekvert, aka VPN. Onion routingen forsikrer klienten om at informasjonen som passerer nodene er kryptert og umulig for hackere å lese med mindre de har tilgang til algoritmen klienten brukte for å enkryptere. Prosjektet har blitt løst ut i fra dette. 
           
### Implementert funksjonalitet
Oppbyggingen av dette prosjektet har blitt utført i tre deler. Dere kan se at under "branches" følgende inndelinger; feature-node-setup, feature-encrypt-decrypt og feature-http-connection. Feature-node-setup tar for seg funsjonaliteten av oppsettningen på tjeneren, klienten og det viktigste som er nodene. Videre, har vi feature-encrypt-decrypt hvor det blir omplementert funksjonalitetene for enkryptering, dekryptering og generering av nøkkler for å ta i bruk i krypteringa. Tilslutt står igjen feature-http-connection som har ansvar for oppkoblings funksjonaliteten mot en http URL.


### Eksterne avhengigheter med en kort beskrivelse av hver avhengighet og hva den er brukt til
    AESSecurityCap
        import javax.crypto.*;
            Brukes for det meste for laging av nøkkler Diffie Hellman og AES krypteringa (KeyAgreement, Cipher)
        import javax.crypto.spec.SecretKeySpec;
            Brukes i generateKey() metoden for laging av hemmlig nøkkel (SecretKeySpec)
        import java.security.*;
            Nøkkelsammensetning av nøkkelpar og hjelper også til for laging av nøkkler
        import java.util.Base64;
             Binær-til-tekst-kodingsskjemaet hjelper til i både enkrypteringa og dekrypteringa 
             
    Client, Server
        import java.io.IOException;
             Sending og mottagelse av datagrampakker
        import java.net.*;
             Gjør det mulig å opprette datagramsocket og opperere med dem
        import java.nio.charset.StandardCharsets;
             Gjøre om til data fra Datapakken til String i for av UTF_8
             
    Main
        import java.util.concurrent.ExecutorService;
              Hjelper til med å opperere med tråer generelt
        import java.util.concurrent.Executors;
              Hjelper til med å sette i gang tråene
              
    Node
        import java.io.IOException;
              Sending og mottagelse av datagrampakker
        import java.net.DatagramPacket;
              Gjør det mulig å opprette og opperere med datagrampakker
        import java.net.DatagramSocket;
              Gjør det mulig å opprette og operere med datagramsocket som gjør som at UDP kan tas i bruk
        import java.net.InetAddress;
              Har ikke oppgitt en spesifikk adresse så denne klassen representerer en ip adresse og er egnet for bruken av UDP
        import java.net.SocketException;
              Bruker for fangs av feil som kommer av socket-forbindelsen
        import java.nio.charset.StandardCharsets;
              Gjøre om til data fra Datapakken til String i for av UTF_8
        
        
### Installasjonsinstruksjoner
1. Det brukes Java 14 for dette prosjektet så det kan være lurt at du laster ned java 14.0.2:
        https://www.oracle.com/java/technologies/javase/jdk14-archive-downloads.html
2. Klone prosjektet med enten SSH eller HTTPS
        Åpne terminalen og pass på at du står inn på den mappa du ønsker å klone prosjektet inn i
            Dette kan gjøre ved å skrive: 
            cd (veien til mappa)
        For å klone skriver du:
     
    HTTPS:
    ```
    git clone https://github.com/thadshap/Network-Programming-Onion-Routing-Project.git
    ```
    
    SSH:
    ```
    git clone git@github.com:thadshap/Network-Programming-Onion-Routing-Project.git 
    ```
    
3. Åpne prosjektet i IntelliJ IDEA
         Hvis du ikke har IntelliJ IDEA kan du laste den ned ved hjelp av denne lenken:
             https://www.jetbrains.com/idea/ 
             
             
### Instruksjoner for å bruke løsningen
 1. Gå inn på Main.java
 2. På den øverste linja i koden kan man legge til den http nettsiden man ønsker å gå inn på gjennom onion routing
 3. Så kan koden kjøres ved å trykke på den grønne "play"-knappen til venstre for initialiseringen av main metoden eller initialiseringen av Main klassen. Hvis du ikke finner disse knappene så kan du høyereklikke hvor som helt inne på klassen og trykke på Run 'Main.main()'.
 4. Hvis dette funket så skal du få dette opp under Run-terminalen:
    ```
    CLIENT: sends http://example.com
    SERVER receives: http://example.com
    CLIENT: received HTTP-response code 200
    ```     
         
### Fremtidig arbeid med oversikt over nåværende mangler/svakheter
- Gjøre om til Maven prosjekt
- Skulle gjerne hatt med SSH for å komme seg inn på HTTPS lenker 
- Skulle laget tester og kjørt continuous integration/deployment


### Eventuell lenke til API dokumentasjon
    https://download.java.net/java/GA/jdk14/docs/api/java.base/java/net/DatagramPacket.html#%3Cinit%3E(byte%5B%5D,int,java.net.InetAddress,int)
    https://download.java.net/java/GA/jdk14/docs/api/java.base/java/net/DatagramSocket.html#send(java.net.DatagramPacket)
    https://docs.oracle.com/en/java/javase/15/docs/api/java.base/java/lang/Runnable.html#run()
    https://docs.oracle.com/en/java/javase/15/docs/api/java.base/java/util/concurrent/ExecutorService.html
    
    
### Ekstra lenker 
    https://stackoverflow.com/questions/21081713/diffie-hellman-key-exchange-in-java 
    https://github.com/dangeabunea/RomanianCoderExamples/blob/master/UdpUnicastSimple/src/romaniancoder/networking/udp/unicast/simple/Main.java 
     
### Forfatter
Thadshajini Paramsothy
