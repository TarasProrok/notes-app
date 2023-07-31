CREATE TABLE access.families (
	id UUID,
	title VARCHAR(255) NOT NULL,
	code VARCHAR(36) NOT NULL,
	CONSTRAINT families_pkey PRIMARY KEY (id)
);

ALTER TABLE access.users
ADD family_id UUID NULL;

ALTER TABLE access.users
ADD FOREIGN KEY (family_id) REFERENCES access.families(id);