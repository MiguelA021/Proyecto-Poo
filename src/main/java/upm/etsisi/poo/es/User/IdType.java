package upm.etsisi.poo.es.User;

public enum IdType {
    DNI, NIE, COMPANY, INVALID;

    public static IdType detect(String id) {

        if (id.matches("^[0-9]{8}[A-Z]$")) return IdType.DNI;

        if (id.matches("^[XYZ][0-9]{7}[A-Z]$")) return IdType.NIE;

        if (id.matches("^[A-W][0-9]{7}[0-9A-Z]$"))
            return IdType.COMPANY;

        return IdType.INVALID;
    }
}
