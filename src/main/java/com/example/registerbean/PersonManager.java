package com.example.registerbean;

import lombok.Data;

/**
 * @author 李佳明 https://github.com/pkpk1234/
 * @date 2017-10-30
 */
@Data
public class PersonManager {

    private PersonDao personDao;

    public Person newPerson() {
        return this.personDao.createDemoPerson();
    }

}
