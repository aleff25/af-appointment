CREATE TABLE IF NOT EXISTS messages(
                                       id             VARCHAR(256) NOT NULL,
                                       created_at     timestamp(6),
                                       updated_at     timestamp(6),
                                       message        TEXT,
                                       author_id      VARCHAR(256),
                                       appointment_id VARCHAR(256),
                                       PRIMARY KEY (id),

                                       CONSTRAINT FK_notes_author FOREIGN KEY (author_id)
                                           REFERENCES users (id)
                                           ON DELETE NO ACTION
                                           ON UPDATE NO ACTION,
                                       CONSTRAINT FK_notes_appointment FOREIGN KEY (appointment_id)
                                           REFERENCES appointments (id)
                                           ON DELETE NO ACTION
                                           ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS exchanges (
    id VARCHAR(256) NOT NULL,
    exchange_status VARCHAR(256),
    appointment_requestor_id VARCHAR(256),
    appointment_requested_id VARCHAR(256),
    created_at timestamp(6),
    last_modified_at timestamp(6),
  PRIMARY KEY (id),
	CONSTRAINT FK_exchange_appointment_requestor FOREIGN KEY (appointment_requestor_id)
  REFERENCES appointments (id)
	ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  	CONSTRAINT FK_exchange_appointment_requested FOREIGN KEY (appointment_requested_id)
  REFERENCES appointments (id)
	ON DELETE NO ACTION
  ON UPDATE NO ACTION
);
