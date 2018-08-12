ALTER TABLE t_org CHANGE loan_limit loan_limit_min BIGINT(20) NOT NULL;
ALTER TABLE t_org ADD loan_limit_max BIGINT(20) NOT NULL;
ALTER TABLE t_org CHANGE interest_rate interest_rate_min VARCHAR(255);
ALTER TABLE t_org ADD interest_rate_max VARCHAR(255) NULL;