package ru.sumarokov.premium_calculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.sumarokov.premium_calculation.config.AbstractApplicationTest;

public class StatisticsServiceTest extends AbstractApplicationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private StatisticsService statisticsService;

    @BeforeClass
    protected void SetUp() {
        String countUsers = "1000000";
        String countCredits = "1000000";

        // Удаляем таблицы если они есть
        String setUpSQL =
                        "ALTER TABLE users DROP CONSTRAINT username_unique;\n" +

                        // Чистим все таблицы
                        "truncate product_group cascade;\n" +
                        "truncate credit cascade;\n" +
                        "truncate preliminary_credit_result cascade;\n" +
                        "truncate efficiency cascade;\n" +
                        "truncate criteria_bonus_for_fur cascade;\n" +
                        "truncate fur_result cascade;\n" +
                        "truncate productivity_level cascade;\n" +
                        "truncate productivity_result cascade;\n" +
                        "truncate premium_limit cascade;\n" +
                        "truncate insurance_result cascade;\n" +
                        "truncate users cascade;\n" +
                        "truncate insurance cascade;\n" +

                        // Заполняем таблицы дефолтными данными
                        // Необходимыми для создания основных данных для теста
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

                        // Добавляем скрипт необходимый для создания рандомных текстовых данных
                        "CREATE OR REPLACE FUNCTION base26_encode(IN digits bigint, IN min_width int = 0)\n" +
                        "  RETURNS varchar AS $$\n" +
                        "        DECLARE\n" +
                        "          chars char[];\n" +
                        "          ret varchar;\n" +
                        "          val bigint;\n" +
                        "      BEGIN\n" +
                        "      chars := ARRAY['A','B','C','D','E','F','G','H','I','J','K','L','M'\n" +
                        "                    ,'N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];\n" +
                        "      val := digits;\n" +
                        "      ret := '';\n" +
                        "      IF val < 0 THEN\n" +
                        "          val := val * -1;\n" +
                        "      END IF;\n" +
                        "      WHILE val != 0 LOOP\n" +
                        "          ret := chars[(val % 26)+1] || ret;\n" +
                        "          val := val / 26;\n" +
                        "      END LOOP;\n" +
                        "\n" +
                        "      IF min_width > 0 AND char_length(ret) < min_width THEN\n" +
                        "          ret := lpad(ret, min_width, '0');\n" +
                        "      END IF;\n" +
                        "\n" +
                        "      RETURN ret;\n" +
                        " \n" +
                        "END;\n" +
                        "$$ LANGUAGE 'plpgsql' IMMUTABLE;\n" +

                        // Заполняем таблицу users данными необходимыми для тестирования
                        "INSERT INTO users (\n" +
                        "username,\n" +
                        "password,\n" +
                        "email,\n" +
                        "role\n" +
                        ")\n" +
                        "SELECT\n" +
                        "  initcap(base26_encode(substring(random()::text,3,12)::bigint)) as username\n" +
                        ", initcap(base26_encode(substring(random()::text,3,9)::bigint)) as password\n" +
                        ", initcap(base26_encode(substring(random()::text,3,9)::bigint)) as email\n" +
                        ", 'ROLE_CREDIT_SPECIALIST' as role\n" +
                        "FROM generate_series(1," + countUsers + ") num;" +

                        // Заполняем таблицу credit данными необходимыми для тестирования
                        "INSERT INTO credit (\n" +
                        "product_group_id, \n" +
                        "amount, \n" +
                        "term, \n" +
                        "rate, \n" +
                        "insurance_id,\n" +
                        "is_connected_sms,\n" +
                        "is_fur,\n" +
                        "is_consultant_availability,\n" +
                        "is_used_self_reject,\n" +
                        "users_id\n" +
                        ")\n" +
                        "SELECT \n" +
                        "  ceil(random()*7 + (SELECT id FROM product_group ORDER BY id LIMIT 1)) as product_group_id\n" +
                        ", ceil(random()*1000000 + 1) as amount\n" +
                        ", ceil(random()*59 + 1) as term\n" +
                        ", ceil(random()*29 + 1) as rate\n" +
                        ", ceil(random()*3 + (SELECT id FROM insurance ORDER BY id LIMIT 1)) as insurance_id\n" +
                        ", random() < 0.5 as is_connected_sms\n" +
                        ", random() < 0.5 as is_fur\n" +
                        ", random() < 0.5 as is_consultant_availability\n" +
                        ", random() < 0.5 as is_used_self_reject\n" +
                        ", random()*(" + countUsers + "- 1) + (SELECT id FROM users ORDER BY id LIMIT 1) as users_id\n" +
                        "FROM generate_series(1," + countCredits + ") num;" +

                        // Заполняем таблицу efficiency данными необходимыми для тестирования
                        "INSERT INTO efficiency (\n" +
                        "total_premium,\n" +
                        "premium_for_credits,\n" +
                        "fur_bonus,\n" +
                        "total_productivity,\n" +
                        "premium_insurance,\n" +
                        "users_id\n" +
                        ")\n" +
                        "SELECT \n" +
                        "  ceil(random()*1000000) as total_premium\n" +
                        ", ceil(random()*1000000) as premium_for_credits\n" +
                        ", ceil(random()*1000000) as fur_bonus\n" +
                        ", ceil(random()*1000000) as total_productivity\n" +
                        ", ceil(random()*1000000) as premium_insurance\n" +
                        ", ceil(num) as users_id\n" +
                        "FROM generate_series(" +
                        "(SELECT id FROM users LIMIT 1)" +
                        ", (SELECT id FROM users ORDER BY id LIMIT 1) +" + countUsers + "- 1) num;" +

                        // Заполняем таблицу fur_result данными необходимыми для тестирования
                        "INSERT INTO fur_result (\n" +
                        "bonus,\n" +
                        "count_credits_category_fur,\n" +
                        "count_credits_category_fur_with_sms,\n" +
                        "penetration_sms_credits_category_fur,\n" +
                        "sum_amount_credits_category_fur,\n" +
                        "users_id\n" +
                        ")\n" +
                        "SELECT\n" +
                        "  ceil(random()*1000000) as bonus\n" +
                        ", ceil(random()*1000000) as count_credits_category_fur\n" +
                        ", ceil(random()*1000000) as count_credits_category_fur_with_sms\n" +
                        ", ceil(random()*100) as penetration_sms_credits_category_fur\n" +
                        ", ceil(random()*1000000) as sum_amount_credits_category_fur\n" +
                        ", ceil(num) as users_id\n" +
                        "FROM generate_series(" +
                        "(SELECT id FROM users LIMIT 1)" +
                        ", (SELECT id FROM users ORDER BY id LIMIT 1) +" + countUsers + "- 1) num;" +

                        // Заполняем таблицу insurance_result данными необходимыми для тестирования
                        "INSERT INTO insurance_result (\n" +
                        "total_bonus,\n" +
                        "penetration,\n" +
                        "sum_insurance_volume,\n" +
                        "users_id\n" +
                        ")\n" +
                        "SELECT\n" +
                        "  ceil(random()*1000000) as total_bonus\n" +
                        ", ceil(random()*200) as penetration\n" +
                        ", ceil(random()*1000000) as sum_insurance_volume\n" +
                        ", ceil(num) as users_id\n" +
                        "FROM generate_series(" +
                        "(SELECT id FROM users LIMIT 1)" +
                        ", (SELECT id FROM users ORDER BY id LIMIT 1) +" + countUsers + "- 1) num;";

        jdbcTemplate.update(setUpSQL);
    }

    @AfterClass
    protected void afterClass() {
        jdbcTemplate.update("" +
                "truncate product_group cascade;\n" +
                "truncate credit cascade;\n" +
                "truncate preliminary_credit_result cascade;\n" +
                "truncate efficiency cascade;\n" +
                "truncate criteria_bonus_for_fur cascade;\n" +
                "truncate fur_result cascade;\n" +
                "truncate productivity_level cascade;\n" +
                "truncate productivity_result cascade;\n" +
                "truncate premium_limit cascade;\n" +
                "truncate insurance_result cascade;\n" +
                "truncate users cascade;\n" +
                "truncate insurance cascade;\n" +
                "ALTER TABLE users ADD CONSTRAINT username_unique UNIQUE (username);");
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
