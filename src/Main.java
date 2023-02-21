import java.util.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        List<Thread> list = new ArrayList<>();

        String[] texts = new String[25];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("aab", 30_000);
        }
        long startTs = System.currentTimeMillis(); // start time

        for (int a = 0; a < texts.length; a++) {

            int finalA = a;
            Runnable runnable = () -> {
                int maxSize = 0;
                for (int i = 0; i < texts[finalA].length(); i++) {
                    for (int j = 0; j < texts[finalA].length(); j++) {
                        if (i >= j) {
                            continue;
                        }
                        boolean bFound = false;
                        for (int k = i; k < j; k++) {
                            if (texts[finalA].charAt(k) == 'b') {
                                bFound = true;
                                break;
                            }
                        }
                        if (!bFound && maxSize < j - i) {
                            maxSize = j - i;
                        }
                    }
                }
                System.out.println(texts[finalA].substring(0, 100) + " -> " + maxSize);
            };
            list.add(finalA, new Thread(runnable));
            list.get(finalA).start();
        }
        for (Thread thread : list) {
            thread.join();
        }

        long endTs = System.currentTimeMillis(); // end time

        System.out.println("Time: " + (endTs - startTs) + "ms");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}