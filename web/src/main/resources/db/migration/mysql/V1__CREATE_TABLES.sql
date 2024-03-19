CREATE TABLE app_info
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    code         VARCHAR(255) NOT NULL UNIQUE,
    name         VARCHAR(255) NOT NULL,
    summary      TEXT         NULL,
    url          VARCHAR(255) NOT NULL,
    status       INT          NOT NULL DEFAULT 10,
    created_time TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_time TIMESTAMP    NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '应用信息表'
  ROW_FORMAT = Dynamic;

CREATE TABLE api_info
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    app_id       BIGINT       NOT NULL,
    code         VARCHAR(255) NOT NULL UNIQUE,
    name         VARCHAR(255) NOT NULL,
    url          VARCHAR(255) NOT NULL,
    method       VARCHAR(50)  NOT NULL COMMENT '请求方式',
    header       VARCHAR(255) NULL,
    parameter    TEXT         NULL COMMENT '请求参数',
    timeout      INT          NULL COMMENT '请求超时时间（秒）',
    status       INT          NOT NULL DEFAULT 10,
    summary      TEXT         NULL,
    created_time TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_time TIMESTAMP    NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '接口信息表'
  ROW_FORMAT = Dynamic;
