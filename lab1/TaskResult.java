package JW_LAB1;

public class TaskResult {
    private Integer countwords;

    public TaskResult() {
        countwords = 0;
    }

    public TaskResult(Integer countwords) {
        this.countwords = countwords;
    }

    public void addMatchedWords(int count) {
        countwords += count;
    }

    public Integer getMatchedWords() {
        return countwords;
    }
}

