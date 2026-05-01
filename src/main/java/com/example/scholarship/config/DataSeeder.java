package com.example.scholarship.config;

import com.example.scholarship.model.Users;
import com.example.scholarship.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository UsersRepository;

    @Override
    public void run(String... args) throws Exception {
        if (UsersRepository.count() > 0) return;

        List<Users> users = List.of(
                Users.builder().fullName("Admin 1 Rebero").email("admin1@gmail.com").password("oooo").role("admin").build(),
                Users.builder().fullName("BK Kis Brch").email("bkkisimenti@gmail.com").password("oooo").role("sponsor").build(),
                Users.builder().fullName("Min Rwanda").email("minrwa@gmail.com").password("oooo").role("sponsor").build(),
                Users.builder().fullName("Stack Rw LLC").email("stack.info@gmail.com").password("ssss").role("sponsor").build(),
                Users.builder().fullName("China Rw Inc").email("chinarw@gmail.com").password("oooo").role("sponsor").build(),
                Users.builder().fullName("Min y'ubuzima").email("minubuzima@gmail.com").password("oooooooo").role("sponsor").build(),
                Users.builder().fullName("Min uburezi").email("minuburezi@gmail.com").password("oooo").role("sponsor").build(),
                Users.builder().fullName("Tech Hub Inc").email("techhubkigali.info@outlook.com").password("oooo").role("sponsor").build(),
                Users.builder().fullName("Study Org Rw LLC").email("studyrworga@gmail.com").password("oooo").role("sponsor").build(),
                Users.builder().fullName("Rwanda Women Hub").email("rwwomenhub.info@gmail.com").password("oooooooo").role("sponsor").build(),
                Users.builder().fullName("Kgl Network Hub").email("kglnethub.info@gmail.com").password("oooo").role("sponsor").build(),
                Users.builder().fullName("Igihe Sponsor").email("igihesponsor@gmail.com").password("oooo").role("sponsor").build(),
                Users.builder().fullName("Sponsor9").email("Sponsor9@gmail.com").password("oooo").role("sponsor").build(),
                Users.builder().fullName("Ruiz Kark").email("ruizmark@gmail.com").password("oooo").role("student")
                        .phone("0791897653").year("1").field("Science").highSchool("Int'l High School")
                        .fatherName("Gustavo Ruiz").fatherPhone("0786534789")
                        .motherName("Maria Ruiz Palma").motherPhone("0789274899").build(),
                Users.builder().fullName("KAGAME King Dan").email("kagamekdan@gmail.com").password("oooo").role("student")
                        .age("21").sex("Male").phone("0791775431").year("1").field("Science")
                        .highSchool("Supreme Elite High School")
                        .fatherName("KAGAME Paulin").fatherPhone("0786357819")
                        .motherName("KAGAME Henriette Martine").motherPhone("0793728339").build(),
                Users.builder().fullName("IRAKOZE Patrick").email("irakozep54p@gmail.com").password("iiii").role("student")
                        .age("22").sex("Male").phone("0789743221").year("1").field("Marketing")
                        .highSchool("Men High School")
                        .fatherName("IRAKOZE Delvin").fatherPhone("0723783127")
                        .motherName("INEZA Marlene").motherPhone("0799377888").build(),
                Users.builder().fullName("Tuhizere Karine Elvy").email("tuhizereke@gmail.com").password("tttt").role("student")
                        .age("20").sex("Female").phone("0793913759").year("1").field("MBC")
                        .highSchool("Women Act International College")
                        .fatherName("Romain De HIRWA").fatherPhone("0787972365")
                        .motherName("Karine HIRWA").motherPhone("07138185768").build(),
                Users.builder().fullName("Daniella Righina").email("danielaregina@gmail.com").password("dddd").role("student")
                        .age("19").sex("Female").phone("0798987625").year("1").field("Economy")
                        .highSchool("Immaculate Conception")
                        .fatherName("Bastien Romain").fatherPhone("0798382293")
                        .motherName("Arielle Romain").motherPhone("0723722828").build(),
                Users.builder().fullName("NYARAHIBINEZA Francine").email("nyara@gmail.com").password("nnnn").role("student")
                        .age("22").sex("Female").phone("07983664789").year("1").field("Business")
                        .highSchool("Bessieux")
                        .fatherName("Omer Kasper").fatherPhone("07923787879")
                        .motherName("Nina Kasper").motherPhone("0787367528").build(),
                Users.builder().fullName("Alex Mugisha").email("mugisha45Alex@gmail.com").password("mmmm").role("student")
                        .age("20").sex("Male").phone("0797234782").year("1").field("Business")
                        .highSchool("Saint-George")
                        .fatherName("Mugisha Ange").fatherPhone("0878979869")
                        .motherName("Symaloe Angelle").motherPhone("9778665675").build(),
                Users.builder().fullName("Axel Bridgeston KAGAME").email("kagameAxel9@gmail.com").password("kkkk").role("student")
                        .age("22").sex("Male").phone("0793272885").year("2").field("Business")
                        .highSchool("Immaculate Conception")
                        .fatherName("KAGAME Jean-Marc").fatherPhone("0734272827")
                        .motherName("Angelle KAGAME").motherPhone("0734746278").build(),
                Users.builder().fullName("Henriette Ania De la Vega").email("henrietteVega@gmail.com").password("hhhhhhhh").role("student")
                        .age("22").sex("Female").phone("0765373838").year("2").field("Business")
                        .highSchool("International Kigali College")
                        .fatherName("Karlo De la Vega").fatherPhone("07836423828")
                        .motherName("Henriette De la Vega").motherPhone("07989827682").build(),
                Users.builder().fullName("NZE MBA Samy").email("mbaNzegbn@gmail.com").password("mmmmmmmm").role("student")
                        .age("21").sex("Female").phone("0795343828").year("2").field("Science (MBC)")
                        .highSchool("Int'l Kigali Immculate Conception")
                        .fatherName("NZE OBAME Arsene Dupont").fatherPhone("0791828893")
                        .motherName("Vicky NGUEMA epouse NZE MBA").motherPhone("07999463745").build()
        );

        UsersRepository.saveAll(users);
        System.out.println("Database seeded with " + users.size() + " Users.");
    }
}