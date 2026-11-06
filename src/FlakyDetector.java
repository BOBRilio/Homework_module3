import java.util.*;
import java.util.stream.Collectors;

public class FlakyDetector {
    Set<String> failed(List<String> run) {//имена упавших тестов прогона
        Set<String> testsFaled = new HashSet<>();
        for (String line : run) {
            String[] lineParts = line.split("\\|", -1);
            if (lineParts[1].trim().equals("FAILED")) {
                testsFaled.add(lineParts[0].trim());
            }

        }
        return testsFaled;
    }

    Set<String> passed(List<String> run) { //имена прошедших тестов прогона
        Set<String> testsPassed = new HashSet<>();
        for (String line : run) {
            String[] lineParts = line.split("\\|", -1);
            if (lineParts[1].trim().equals("PASSED")) {
                testsPassed.add(lineParts[0].trim());
            }

        }
        return testsPassed;
    }

    List<String> flaky(List<String> run1, List<String> run2){//тесты, которые упали в первом прогоне и прошли во втором
        Set<String> failedRun1 = failed(run1);
        Set<String> passedRun2 = passed(run2);


        failedRun1.retainAll(passedRun2);// оставляем в первом пройденые во втором
//        List<String> res = new ArrayList<>(failedRun1);
//        Collections.sort(res);

        return  new ArrayList<>(failedRun1);
    }

    Map<String, Long> statusSummary(List<String> run){//сколько тестов в каждом статусе
        return run.stream()
                .map(line -> line.split("\\|", -1))
                .map(parts -> parts[1].trim())
                .collect(Collectors.groupingBy(
                        status -> status,
                        Collectors.counting()
                ));
    }

    void main(){
        Map<List<String>,Integer> map = new HashMap<>();// сделано просто для счетчика прогонов (костыль)
        List<String> run1 = List.of(
                "LoginTest|PASSED",
                "CreateTaskTest|FAILED",
                "CommentTest|FAILED",
                "LogoutTest|PASSED"
        );


        List<String> run2 = List.of(
                "LoginTest|PASSED",
                "CreateTaskTest|PASSED",
                "CommentTest|FAILED",
                "StatsTest|PASSED"
        );
        map.put(run1,1);
        map.put(run2,2);

        List<String> flakyTests = flaky(run1, run2);
        System.out.println("Flaky-кандидаты: " + flakyTests);


        Map<String, Long> summary = statusSummary(run2);
        System.out.println("Прогон " + map.get(run2) + ", сводка: " + summary);



    }




}

