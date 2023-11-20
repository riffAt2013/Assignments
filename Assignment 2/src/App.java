public class App {
    public static void main(String[] args) {

        AbstractStringSearch search = new StringSearch();
        System.out.println(search.search("Ba?Ma?n", "Balman hello"));
        System.out.println(search.search("+?779", "His cell no. can be +3779"));
        System.out.println(search.search("AB?CD", "AABCCDE"));
    }
}
