public class App {
    public static void main(String[] args) throws Exception {
        //Definimos un texto a cifrar
        String str = "Este es el texto a cifrar";

        System.out.println("\nTexto a cifrar:");
        System.out.println(str);

        //Instanciamos la clase
        RSA rsa = new RSA();

        //Generamos un par de claves
        //Admite claves de 512, 1024, 2048 y 4096 bits
        rsa.genKeyPair(4096);


        String file_private = "./src/main/resources/rsa.pri.txt";
        String file_public = "./src/main/resources/rsa.pub.txt";

        //Las guardamos asi podemos usarlas despues
        //a lo largo del tiempo
        rsa.saveToDiskPrivateKey("./src/main/resources/rsa.pri.txt");
        rsa.saveToDiskPublicKey("./src/main/resources/rsa.pub.txt");

        //Ciframos y e imprimimos, el texto cifrado
        //es devuelto en la variable secure
        String secure = rsa.Encrypt(str);

        System.out.println("\nCifrado:");
        System.out.println(secure);



        //A modo de ejemplo creamos otra clase rsa
        RSA rsa2 = new RSA();

        //A diferencia de la anterior aca no creamos
        //un nuevo par de claves, sino que cargamos
        //el juego de claves que habiamos guadado
        rsa2.openFromDiskPrivateKey("./src/main/resources/rsa.pri.txt");
        rsa2.openFromDiskPublicKey("./src/main/resources/rsa.pub.txt");

        //Le pasamos el texto cifrado (secure) y nos
        //es devuelto el texto ya descifrado (unsecure)
        String unsecure = rsa2.Decrypt(secure);

        //Imprimimos
        System.out.println("\nDescifrado:");
        System.out.println(unsecure);



    }
}
