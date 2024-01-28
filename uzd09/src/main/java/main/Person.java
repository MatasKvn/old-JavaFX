package main;

public class Person {



    private String first_name;
    private String last_name;
    private String email;
    private String imagelink;
    private String ip_address;
    private String ipFirstDigits;

    private String inputString;

    public Person(String inputString){
        if(inputString != null){
            try {
                String[] parts = inputString.split(";");
                first_name = parts[0];
                last_name = parts[1];
                email = parts[2];
                imagelink = parts[3];
                ip_address = parts[4];
                ipFirstDigits = "";

                for(int i=0; ip_address.charAt(i) != '.' && i<3; ++i){
                    ipFirstDigits += ip_address.charAt(i);
                }

            }catch (Exception e){
                System.out.println("Invalid data.");
            }
        }
    }

    @Override
    public String toString(){
        String s = String.format("%s %s, %s, %s, %s", first_name, last_name, email, imagelink, ip_address);
        return s;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public String getImagelink() {
        return imagelink;
    }

    public String getIp_address() {
        return ip_address;
    }

    public String getIpFirstDigits() {
        return ipFirstDigits;
    }

    public String getInputString() {
        return inputString;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImagelink(String imagelink) {
        this.imagelink = imagelink;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public void setIpFirstDigits(String ipFirstDigits) {
        this.ipFirstDigits = ipFirstDigits;
    }

    public void setInputString(String inputString) {
        this.inputString = inputString;
    }
}




















