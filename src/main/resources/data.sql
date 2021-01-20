SET @firstId = UUID();
SET @secondId = UUID();

INSERT INTO agency (id, name, created_at, updated_at) VALUES 
  (@firstId, "Agency 1", CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
  (@secondId, "Agency 2", CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

INSERT INTO dispatcher (id, name, agency_id, username, password, created_at, updated_at) VALUES 
  (UUID(), "Dispatcher 1", @firstId, "dispatcher1", "123456", CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
  (UUID(), "Dispatcher 2", @firstId, "dispatcher2", "123456",CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
  (UUID(), "Dispatcher 3", @secondId, "dispatcher3", "123456",CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
  (UUID(), "Dispatcher 4", @secondId, "dispatcher4", "123456",CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

INSERT INTO responder (id, name, agency_id, created_at, updated_at) VALUES 
  (UUID(), "OFFICER_001", @firstId, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
  (UUID(), "OFFICER_002", @firstId, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
  (UUID(), "OFFICER_003", @secondId, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
  (UUID(), "OFFICER_004", @secondId, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
