package ru.sumarokov.premium_calculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.sumarokov.premium_calculation.config.AbstractApplicationTest;
import ru.sumarokov.premium_calculation.entity.Credit;
import ru.sumarokov.premium_calculation.entity.Insurance;
import ru.sumarokov.premium_calculation.entity.ProductGroup;
import ru.sumarokov.premium_calculation.entity.User;
import ru.sumarokov.premium_calculation.helper.Role;
import ru.sumarokov.premium_calculation.repository.InsuranceRepository;
import ru.sumarokov.premium_calculation.repository.ProductGroupRepository;
import ru.sumarokov.premium_calculation.repository.UserRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StatisticsServiceTest extends AbstractApplicationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductGroupRepository productGroupRepository;
    @Autowired
    private InsuranceRepository insuranceRepository;
    @Autowired
    private CreditService creditService;
    @Autowired
    private StatisticsService statisticsService;

    @BeforeClass
    @Transactional
    protected void SetUp() {
        jdbcTemplate.update("truncate insurance cascade;");
        jdbcTemplate.update("truncate product_group cascade;");
        jdbcTemplate.update("truncate credit cascade;");
        jdbcTemplate.update("truncate preliminary_credit_result cascade;");
        jdbcTemplate.update("truncate efficiency cascade;");
        jdbcTemplate.update("truncate criteria_bonus_for_fur cascade;");
        jdbcTemplate.update("truncate fur_result cascade;");
        jdbcTemplate.update("truncate productivity_level cascade;");
        jdbcTemplate.update("truncate productivity_result cascade;");
        jdbcTemplate.update("truncate premium_limit cascade;");
        jdbcTemplate.update("truncate insurance_result cascade;");
        jdbcTemplate.update("truncate users cascade;");
        jdbcTemplate.update(
                "INSERT INTO premium_limit(max_total_premium, is_actual)\n" +
                        "VALUES (100000, 'true');\n" +
                        "\n" +
                        "INSERT INTO insurance(name, factor_insurance_volume, factor_insurance_bonus)\n" +
                        "VALUES\n" +
                        "('Нет', 0, 0),\n" +
                        "('Жизнь', 100, 1.6),\n" +
                        "('Безработица', 100, 1.4),\n" +
                        "('Комбо', 200, 3);\n" +
                        "\n" +
                        "INSERT INTO product_group(name, factor_premium, min_premium, max_premium, type_credit, min_amount_for_calculating_credit_premium)\n" +
                        "VALUES\n" +
                        "('Бытовая техника и электроника', 0.2, 30, 3000, 'POINT_OF_SALE', 0),\n" +
                        "('Мебель, Меха и одежда', 0.09, 30, 3000, 'POINT_OF_SALE', 0),\n" +
                        "('Мобильные телефоны и Ювелирные изделия', 0.06, 30, 1300, 'POINT_OF_SALE', 0),\n" +
                        "('Общая мотивация', 0.13, 30, 3000, 'POINT_OF_SALE', 0),\n" +
                        "('Стройматериалы и Мототехника', 0.13, 30, 3000, 'POINT_OF_SALE', 0),\n" +
                        "('Услуги', 0.13, 30, 3000, 'POINT_OF_SALE', 0),\n" +
                        "('Наличные на карту тип с 1 по 3', 0.6, 0, 6000, 'CASH_ON_CARD', 100000),\n" +
                        "('Наличные на карту тип 4', 0.6, 0, 6000, 'CASH_ON_CARD', 100000);\n" +
                        "\n" +
                        "INSERT INTO criteria_bonus_for_fur( min_sum, min_sms, bonus)\n" +
                        "VALUES (750000, 70, 7500);\n" +
                        "\n" +
                        "INSERT INTO productivity_level(name, premium, min_count_credits, min_sum_amount_credits, min_penetration_sms, min_penetration_insurance, is_default)\n" +
                        "VALUES\n" +
                        "('Уровень 0', 0, 0, 0, 0, 0, true),\n" +
                        "('Медный', 7000, 16, 500000, 75, 0, false),\n" +
                        "('Бронзовый', 10000, 21, 650000, 85, 30, false),\n" +
                        "('Серебрянный', 15000, 30, 1000000, 90, 40, false),\n" +
                        "('Золотой', 20000, 36, 1500000, 95, 60, false);");

        //Создание и сохраннение юзеров в БД
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            int leftLimit = 48;
            int rightLimit = 122;
            int targetStringLength = 10;
            Random random = new Random();

            String string = random.ints(leftLimit, rightLimit + 1)
                    .filter(j -> (j <= 57 || j >= 65) && (j <= 90 || j >= 97))
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

            User user = new User(string, string, string + "@mail.ru", Role.ROLE_CREDIT_SPECIALIST);
            users.add(user);
            System.out.println("userCreate" + i);
        }
        users = userRepository.saveAll(users);

        // Создание и сохранение оформленных кредитов через CreditService
        ProductGroup productGroup = productGroupRepository.findAll().get(0);
        Insurance insurance = insuranceRepository.findAll().get(0);
        for (int i = 0; i < users.size() - 1; i++) {
            System.out.println("userUpdate" + i);
            for (int j = 0; j < 10; j++) {
                creditService.saveCredit(
                        new Credit(productGroup, BigDecimal.valueOf(100000), 12, BigDecimal.valueOf(10), insurance, true, false, false, false, users.get(i)), users.get(i));
            }
        }
    }

    @Test
    public void getAverageInsurancePenetrationPercentage() {
        System.out.println(statisticsService.getAverageInsurancePenetrationPercentage());
    }

    @Test
    public void getCountCreditSpecialist() {
        System.out.println(statisticsService.getCountCreditSpecialist());
    }

    @Test
    public void getSumAmountCredits() {
        System.out.println(statisticsService.getSumAmountCredits());
    }

    @Test
    public void getSumTotalPremiumSpecialists() {
        System.out.println(statisticsService.getSumTotalPremiumSpecialists());
    }

    @Test
    public void getTotalInsuredVolumeCredits() {
        System.out.println(statisticsService.getTotalInsuredVolumeCredits());
    }

    @Test
    public void getTotalVolumeCreditsForFurs() {
        System.out.println(statisticsService.getTotalVolumeCreditsForFurs());
    }
}
