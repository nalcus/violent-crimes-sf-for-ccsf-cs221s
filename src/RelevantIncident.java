public class RelevantIncident implements Comparable<RelevantIncident> {
    String Category;
    int Date;
    String PdDistrict;

    public RelevantIncident(String category, int date, String pdDistrict) {
        Category = category;
        Date = date;
        PdDistrict = pdDistrict;
    }

    public String getCategory() {
        return Category;
    }

    public int getDate() {
        return Date;
    }

    public String getPdDistrict() {
        return PdDistrict;
    }

    @Override
    public String toString() {
        return "RelevantIncident{" +
                "Category='" + Category + '\'' +
                ", Date='" + Date + '\'' +
                ", PdDistrict='" + PdDistrict + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RelevantIncident other = (RelevantIncident) o;

        if (this.Category.equals(other.getCategory()) &&
        this.Date==other.getDate() &&
        this.PdDistrict.equals(other.getPdDistrict()))
            return true;

        return false;
    }

    @Override
    public int compareTo(RelevantIncident incident) {
        if(this.Date == incident.getDate())
            return 0;
        else
            return this.Date > incident.getDate() ? 1 : -1;
    }


}
