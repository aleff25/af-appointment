CREATE TABLE IF NOT EXISTS messages (
    id VARCHAR(256) NOT NULL,
    created_at timestamp(6),
    message TEXT,
    id_author VARCHAR(256),
    id_appointment VARCHAR(256),
    PRIMARY KEY (id),

    CONSTRAINT FK_notes_author FOREIGN KEY (id_author)
        REFERENCES users (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    CONSTRAINT FK_notes_appointment FOREIGN KEY (id_appointment)
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
