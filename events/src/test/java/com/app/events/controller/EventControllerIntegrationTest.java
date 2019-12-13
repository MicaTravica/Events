package com.app.events.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.util.HashSet;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.app.events.constants.EventConstants;
import com.app.events.constants.HallConstants;
import com.app.events.constants.MediaConstants;
import com.app.events.constants.SectorConstants;
import com.app.events.constants.UserConstants;
import com.app.events.dto.EventDTO;
import com.app.events.dto.HallDTO;
import com.app.events.dto.LoginDTO;
import com.app.events.dto.MediaDTO;
import com.app.events.dto.PlaceDTO;
import com.app.events.dto.PriceListDTO;
import com.app.events.dto.SectorDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EventControllerIntegrationTest {

	private String authTokenAdmin = "";

	@Autowired
	private TestRestTemplate restTemplate;

	@Before
	public void login() throws Exception {
		LoginDTO loginDto = new LoginDTO(UserConstants.DB_ADMIN_USERNAME, UserConstants.DB_ADMIN_PASSWORD);
		ResponseEntity<String> response = restTemplate.postForEntity("/api/login", loginDto, String.class);
		authTokenAdmin = response.getBody();
	}

	@Test
	public void getEvent_valid() throws Exception {
		URI uri = new URI(EventConstants.URL_PREFIX + "/" + EventConstants.PERSISTED_EVENT_ID);

		ResponseEntity<EventDTO> response = restTemplate.getForEntity(uri, EventDTO.class);
		EventDTO found = response.getBody();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(found);
		assertEquals(EventConstants.PERSISTED_EVENT_ID, found.getId());
		assertEquals(EventConstants.PERSISTED_EVENT_NAME, found.getName());
		assertEquals(EventConstants.PERSISTED_EVENT_DESCRIPTION, found.getDescription());
		assertEquals(EventConstants.PERSISTED_EVENT_EVENT_STATE, found.getEventState());
		assertEquals(EventConstants.PERSISTED_EVENT_EVENT_TYPE, found.getEventType());
		assertEquals(EventConstants.PERSISTED_EVENT_FROM_DATE, found.getFromDate());
		assertEquals(EventConstants.PERSISTED_EVENT_TO_DATE, found.getToDate());
	}

	@Test
	public void getEvent_throwsResourceNotFoundException() throws Exception {
		URI uri = new URI(EventConstants.URL_PREFIX + "/" + EventConstants.INVALID_EVENT_ID);

		ResponseEntity<Object> response = restTemplate.getForEntity(uri, Object.class);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void create_valid() throws Exception {
		URI uri = new URI(EventConstants.URL_PREFIX);

		HashSet<SectorDTO> sectors = new HashSet<>();
		sectors.add(new SectorDTO(SectorConstants.PERSISTED_SECTOR_ID, "", 0, 0, 0, HallConstants.PERSISTED_HALL_ID));

		HashSet<HallDTO> halls = new HashSet<>();
		halls.add(new HallDTO(HallConstants.PERSISTED_HALL_ID, HallConstants.PERSISTED_HALL_NAME,
				new PlaceDTO(HallConstants.PERSISTED_HALL_PLACE_ID, "", "", 0, 0), sectors));

		HashSet<MediaDTO> mediaList = new HashSet<>();
		mediaList.add(new MediaDTO(null, MediaConstants.NEW_MEDIA_PATH, null));

		HashSet<PriceListDTO> priceLists = new HashSet<>();
		priceLists.add(new PriceListDTO(null, 100.0, null, SectorConstants.PERSISTED_SECTOR_ID));

		EventDTO event = new EventDTO(EventConstants.NEW_EVENT_ID, EventConstants.NEW_EVENT_NAME,
				EventConstants.NEW_EVENT_DESCRIPTION, EventConstants.NEW_EVENT_FROM_DATE,
				EventConstants.NEW_EVENT_TO_DATE, EventConstants.NEW_EVENT_EVENT_STATE,
				EventConstants.NEW_EVENT_EVENT_TYPE, halls, priceLists, mediaList);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + this.authTokenAdmin);
		HttpEntity<EventDTO> req = new HttpEntity<>(event, headers);

		ResponseEntity<EventDTO> response = restTemplate.exchange(uri, HttpMethod.POST, req, EventDTO.class);
		EventDTO created = response.getBody();

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(created.getId());
		assertEquals(EventConstants.NEW_EVENT_NAME, created.getName());
		assertEquals(EventConstants.NEW_EVENT_DESCRIPTION, created.getDescription());
		assertEquals(EventConstants.NEW_EVENT_FROM_DATE, created.getFromDate());
		assertEquals(EventConstants.NEW_EVENT_TO_DATE, created.getToDate());
		assertEquals(EventConstants.NEW_EVENT_EVENT_STATE, created.getEventState());
		assertEquals(EventConstants.NEW_EVENT_EVENT_TYPE, created.getEventType());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void create_throwsDateException_badDate() throws Exception {
		URI uri = new URI(EventConstants.URL_PREFIX);

		HashSet<SectorDTO> sectors = new HashSet<>();
		sectors.add(new SectorDTO(SectorConstants.PERSISTED_SECTOR_ID, "", 0, 0, 0, HallConstants.PERSISTED_HALL_ID));

		HashSet<HallDTO> halls = new HashSet<>();
		halls.add(new HallDTO(HallConstants.PERSISTED_HALL_ID, HallConstants.PERSISTED_HALL_NAME,
				new PlaceDTO(HallConstants.PERSISTED_HALL_PLACE_ID, "", "", 0, 0), sectors));

		HashSet<MediaDTO> mediaList = new HashSet<>();
		mediaList.add(new MediaDTO(null, MediaConstants.NEW_MEDIA_PATH, null));

		HashSet<PriceListDTO> priceLists = new HashSet<>();
		priceLists.add(new PriceListDTO(null, 100.0, null, SectorConstants.PERSISTED_SECTOR_ID));

		EventDTO event = new EventDTO(EventConstants.NEW_EVENT_ID, EventConstants.NEW_EVENT_NAME,
				EventConstants.NEW_EVENT_DESCRIPTION, EventConstants.EVENT_FROM_DATE_BAD,
				EventConstants.EVENT_TO_DATE_BAD, EventConstants.NEW_EVENT_EVENT_STATE,
				EventConstants.NEW_EVENT_EVENT_TYPE, halls, priceLists, mediaList);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + this.authTokenAdmin);
		HttpEntity<EventDTO> req = new HttpEntity<>(event, headers);

		ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.POST, req, Object.class);

		String message = (String) response.getBody();
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Dates can not be null and to date must be after from date", message);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void create_throwsDateException_hallIsNotAvailable() throws Exception {
		URI uri = new URI(EventConstants.URL_PREFIX);

		HashSet<SectorDTO> sectors = new HashSet<>();
		sectors.add(new SectorDTO(SectorConstants.PERSISTED_SECTOR_ID, "", 0, 0, 0, HallConstants.PERSISTED_HALL_ID));

		HashSet<HallDTO> halls = new HashSet<>();
		halls.add(new HallDTO(HallConstants.PERSISTED_HALL_ID, HallConstants.PERSISTED_HALL_NAME,
				new PlaceDTO(HallConstants.PERSISTED_HALL_PLACE_ID, "", "", 0, 0), sectors));

		HashSet<MediaDTO> mediaList = new HashSet<>();
		mediaList.add(new MediaDTO(null, MediaConstants.NEW_MEDIA_PATH, null));

		HashSet<PriceListDTO> priceLists = new HashSet<>();
		priceLists.add(new PriceListDTO(null, 100.0, null, SectorConstants.PERSISTED_SECTOR_ID));

		EventDTO event = new EventDTO(EventConstants.NEW_EVENT_ID, EventConstants.NEW_EVENT_NAME,
				EventConstants.NEW_EVENT_DESCRIPTION, EventConstants.PERSISTED_EVENT_FROM_DATE,
				EventConstants.PERSISTED_EVENT_TO_DATE, EventConstants.NEW_EVENT_EVENT_STATE,
				EventConstants.NEW_EVENT_EVENT_TYPE, halls, priceLists, mediaList);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + this.authTokenAdmin);
		HttpEntity<EventDTO> req = new HttpEntity<>(event, headers);

		ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.POST, req, Object.class);

		String message = (String) response.getBody();
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Hall is not available in desired period", message);

	}

	@Test
	@Transactional
	@Rollback(true)
	public void create_throwsResourceNotFoundException_badHall() throws Exception {
		URI uri = new URI(EventConstants.URL_PREFIX);

		HashSet<SectorDTO> sectors = new HashSet<>();
		sectors.add(new SectorDTO(SectorConstants.PERSISTED_SECTOR_ID, "", 0, 0, 0, HallConstants.PERSISTED_HALL_ID));

		HashSet<HallDTO> halls = new HashSet<>();
		halls.add(new HallDTO(HallConstants.INVALID_HALL_ID, HallConstants.PERSISTED_HALL_NAME,
				new PlaceDTO(HallConstants.PERSISTED_HALL_PLACE_ID, "", "", 0, 0), sectors));

		HashSet<MediaDTO> mediaList = new HashSet<>();
		mediaList.add(new MediaDTO(null, MediaConstants.NEW_MEDIA_PATH, null));

		HashSet<PriceListDTO> priceLists = new HashSet<>();
		priceLists.add(new PriceListDTO(null, 100.0, null, SectorConstants.PERSISTED_SECTOR_ID));

		EventDTO event = new EventDTO(EventConstants.NEW_EVENT_ID, EventConstants.NEW_EVENT_NAME,
				EventConstants.NEW_EVENT_DESCRIPTION, EventConstants.NEW_EVENT_FROM_DATE,
				EventConstants.NEW_EVENT_TO_DATE, EventConstants.NEW_EVENT_EVENT_STATE,
				EventConstants.NEW_EVENT_EVENT_TYPE, halls, priceLists, mediaList);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + this.authTokenAdmin);
		HttpEntity<EventDTO> req = new HttpEntity<>(event, headers);

		ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.POST, req, Object.class);

		String message = (String) response.getBody();
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("Hall is not found!", message);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void create_throwsResourceNotFoundException_badSector() throws Exception {
		URI uri = new URI(EventConstants.URL_PREFIX);

		HashSet<SectorDTO> sectors = new HashSet<>();
		sectors.add(new SectorDTO(SectorConstants.INVALID_SECTOR_ID, "", 0, 0, 0, HallConstants.PERSISTED_HALL_ID));

		HashSet<HallDTO> halls = new HashSet<>();
		halls.add(new HallDTO(HallConstants.PERSISTED_HALL_ID, HallConstants.PERSISTED_HALL_NAME,
				new PlaceDTO(HallConstants.PERSISTED_HALL_PLACE_ID, "", "", 0, 0), sectors));

		HashSet<MediaDTO> mediaList = new HashSet<>();
		mediaList.add(new MediaDTO(null, MediaConstants.NEW_MEDIA_PATH, null));

		HashSet<PriceListDTO> priceLists = new HashSet<>();
		priceLists.add(new PriceListDTO(null, 100.0, null, SectorConstants.PERSISTED_SECTOR_ID));

		EventDTO event = new EventDTO(EventConstants.NEW_EVENT_ID, EventConstants.NEW_EVENT_NAME,
				EventConstants.NEW_EVENT_DESCRIPTION, EventConstants.NEW_EVENT_FROM_DATE,
				EventConstants.NEW_EVENT_TO_DATE, EventConstants.NEW_EVENT_EVENT_STATE,
				EventConstants.NEW_EVENT_EVENT_TYPE, halls, priceLists, mediaList);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + this.authTokenAdmin);
		HttpEntity<EventDTO> req = new HttpEntity<>(event, headers);

		ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.POST, req, Object.class);

		String message = (String) response.getBody();
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("Sector is not found!", message);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void create_throwsSectorCapacityCanNotBeZero() throws Exception {
		URI uri = new URI(EventConstants.URL_PREFIX);

		HashSet<SectorDTO> sectors = new HashSet<>();
		sectors.add(new SectorDTO(SectorConstants.PERSISTED_SECTOR_ID2, "", 0, 0, 0, HallConstants.PERSISTED_HALL_ID));

		HashSet<HallDTO> halls = new HashSet<>();
		halls.add(new HallDTO(HallConstants.PERSISTED_HALL_ID, HallConstants.PERSISTED_HALL_NAME,
				new PlaceDTO(HallConstants.PERSISTED_HALL_PLACE_ID, "", "", 0, 0), sectors));

		HashSet<MediaDTO> mediaList = new HashSet<>();
		mediaList.add(new MediaDTO(null, MediaConstants.NEW_MEDIA_PATH, null));

		HashSet<PriceListDTO> priceLists = new HashSet<>();
		priceLists.add(new PriceListDTO(null, 100.0, null, SectorConstants.PERSISTED_SECTOR_ID2));

		EventDTO event = new EventDTO(EventConstants.NEW_EVENT_ID, EventConstants.NEW_EVENT_NAME,
				EventConstants.NEW_EVENT_DESCRIPTION, EventConstants.NEW_EVENT_FROM_DATE,
				EventConstants.NEW_EVENT_TO_DATE, EventConstants.NEW_EVENT_EVENT_STATE,
				EventConstants.NEW_EVENT_EVENT_TYPE, halls, priceLists, mediaList);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + this.authTokenAdmin);
		HttpEntity<EventDTO> req = new HttpEntity<>(event, headers);

		ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.POST, req, Object.class);
		String message = (String) response.getBody();

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Sector capacity must be positiv number!", message);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void create_throwsSectorPriceListException() throws Exception {
		URI uri = new URI(EventConstants.URL_PREFIX);

		HashSet<SectorDTO> sectors = new HashSet<>();
		sectors.add(new SectorDTO(SectorConstants.PERSISTED_SECTOR_ID2, "", 0, 0, 10, HallConstants.PERSISTED_HALL_ID));

		HashSet<HallDTO> halls = new HashSet<>();
		halls.add(new HallDTO(HallConstants.PERSISTED_HALL_ID, HallConstants.PERSISTED_HALL_NAME,
				new PlaceDTO(HallConstants.PERSISTED_HALL_PLACE_ID, "", "", 0, 0), sectors));

		HashSet<MediaDTO> mediaList = new HashSet<>();
		mediaList.add(new MediaDTO(null, MediaConstants.NEW_MEDIA_PATH, null));

		HashSet<PriceListDTO> priceLists = new HashSet<>();

		EventDTO event = new EventDTO(EventConstants.NEW_EVENT_ID, EventConstants.NEW_EVENT_NAME,
				EventConstants.NEW_EVENT_DESCRIPTION, EventConstants.NEW_EVENT_FROM_DATE,
				EventConstants.NEW_EVENT_TO_DATE, EventConstants.NEW_EVENT_EVENT_STATE,
				EventConstants.NEW_EVENT_EVENT_TYPE, halls, priceLists, mediaList);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + this.authTokenAdmin);
		HttpEntity<EventDTO> req = new HttpEntity<>(event, headers);

		ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.POST, req, Object.class);
		String message = (String) response.getBody();

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Sector must have price list!", message);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void create_throwsSectorIsNotInThisHallException() throws Exception {
		URI uri = new URI(EventConstants.URL_PREFIX);

		HashSet<SectorDTO> sectors = new HashSet<>();
		sectors.add(
				new SectorDTO(SectorConstants.PERSISTED_SECTOR_ID3, "", 0, 0, 10, HallConstants.PERSISTED_HALL_ID2));

		HashSet<HallDTO> halls = new HashSet<>();
		halls.add(new HallDTO(HallConstants.PERSISTED_HALL_ID, HallConstants.PERSISTED_HALL_NAME,
				new PlaceDTO(HallConstants.PERSISTED_HALL_PLACE_ID, "", "", 0, 0), sectors));

		HashSet<MediaDTO> mediaList = new HashSet<>();
		mediaList.add(new MediaDTO(null, MediaConstants.NEW_MEDIA_PATH, null));

		HashSet<PriceListDTO> priceLists = new HashSet<>();
		priceLists.add(new PriceListDTO(null, 100.0, null, SectorConstants.PERSISTED_SECTOR_ID3));

		EventDTO event = new EventDTO(EventConstants.NEW_EVENT_ID, EventConstants.NEW_EVENT_NAME,
				EventConstants.NEW_EVENT_DESCRIPTION, EventConstants.NEW_EVENT_FROM_DATE,
				EventConstants.NEW_EVENT_TO_DATE, EventConstants.NEW_EVENT_EVENT_STATE,
				EventConstants.NEW_EVENT_EVENT_TYPE, halls, priceLists, mediaList);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + this.authTokenAdmin);
		HttpEntity<EventDTO> req = new HttpEntity<>(event, headers);

		ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.POST, req, Object.class);
		String message = (String) response.getBody();

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Sector is not in chosen hall!", message);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void create_throwsCollectionIsEmptyException() throws Exception {
		URI uri = new URI(EventConstants.URL_PREFIX);

		HashSet<HallDTO> halls = new HashSet<>();

		HashSet<MediaDTO> mediaList = new HashSet<>();
		mediaList.add(new MediaDTO(null, MediaConstants.NEW_MEDIA_PATH, null));

		HashSet<PriceListDTO> priceLists = new HashSet<>();
		priceLists.add(new PriceListDTO(null, 100.0, null, SectorConstants.PERSISTED_SECTOR_ID2));

		EventDTO event = new EventDTO(EventConstants.NEW_EVENT_ID, EventConstants.NEW_EVENT_NAME,
				EventConstants.NEW_EVENT_DESCRIPTION, EventConstants.NEW_EVENT_FROM_DATE,
				EventConstants.NEW_EVENT_TO_DATE, EventConstants.NEW_EVENT_EVENT_STATE,
				EventConstants.NEW_EVENT_EVENT_TYPE, halls, priceLists, mediaList);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + this.authTokenAdmin);
		HttpEntity<EventDTO> req = new HttpEntity<>(event, headers);

		ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.POST, req, Object.class);
		String message = (String) response.getBody();

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("You must chose one hall", message);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void update_valid() throws Exception {
		URI uri = new URI(EventConstants.URL_PREFIX);

		EventDTO event = new EventDTO(EventConstants.PERSISTED_EVENT_ID, EventConstants.UPDATE_EVENT_NAME,
				EventConstants.UPDATE_EVENT_DESCRIPTION, EventConstants.UPDATE_EVENT_FROM_DATE,
				EventConstants.UPDATE_EVENT_TO_DATE, EventConstants.UPDATE_EVENT_EVENT_STATE,
				EventConstants.UPDATE_EVENT_EVENT_TYPE, new HashSet<>(), new HashSet<>(), new HashSet<>());

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + this.authTokenAdmin);
		HttpEntity<EventDTO> req = new HttpEntity<>(event, headers);

		ResponseEntity<EventDTO> response = restTemplate.exchange(uri, HttpMethod.PUT, req, EventDTO.class);
		EventDTO updated = response.getBody();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(EventConstants.PERSISTED_EVENT_ID, updated.getId());
		assertEquals(EventConstants.UPDATE_EVENT_NAME, updated.getName());
		assertEquals(EventConstants.UPDATE_EVENT_DESCRIPTION, updated.getDescription());
		assertEquals(EventConstants.UPDATE_EVENT_FROM_DATE, updated.getFromDate());
		assertEquals(EventConstants.UPDATE_EVENT_TO_DATE, updated.getToDate());
		assertEquals(EventConstants.UPDATE_EVENT_EVENT_STATE, updated.getEventState());
		assertEquals(EventConstants.UPDATE_EVENT_EVENT_TYPE, updated.getEventType());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void update_throwsResourceNotFoundException() throws Exception {
		URI uri = new URI(EventConstants.URL_PREFIX);

		EventDTO event = new EventDTO(EventConstants.INVALID_EVENT_ID, EventConstants.UPDATE_EVENT_NAME,
				EventConstants.UPDATE_EVENT_DESCRIPTION, EventConstants.UPDATE_EVENT_FROM_DATE,
				EventConstants.UPDATE_EVENT_TO_DATE, EventConstants.UPDATE_EVENT_EVENT_STATE,
				EventConstants.UPDATE_EVENT_EVENT_TYPE, new HashSet<>(), new HashSet<>(), new HashSet<>());

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + this.authTokenAdmin);
		HttpEntity<EventDTO> req = new HttpEntity<>(event, headers);

		ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.PUT, req, Object.class);
		String message = (String) response.getBody();

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("Event is not found!", message);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void update_throwsDateException_badDates() throws Exception {
		URI uri = new URI(EventConstants.URL_PREFIX);

		EventDTO event = new EventDTO(EventConstants.PERSISTED_EVENT_ID, EventConstants.UPDATE_EVENT_NAME,
				EventConstants.UPDATE_EVENT_DESCRIPTION, EventConstants.EVENT_FROM_DATE_BAD,
				EventConstants.EVENT_TO_DATE_BAD, EventConstants.UPDATE_EVENT_EVENT_STATE,
				EventConstants.UPDATE_EVENT_EVENT_TYPE, new HashSet<>(), new HashSet<>(), new HashSet<>());

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + this.authTokenAdmin);
		HttpEntity<EventDTO> req = new HttpEntity<>(event, headers);

		ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.PUT, req, Object.class);
		String message = (String) response.getBody();

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Dates can not be null and to date must be after from date", message);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void update_throwsBadEventStateException() throws Exception {
		URI uri = new URI(EventConstants.URL_PREFIX);

		EventDTO event = new EventDTO(EventConstants.PERSISTED_EVENT_ID, EventConstants.UPDATE_EVENT_NAME,
				EventConstants.UPDATE_EVENT_DESCRIPTION, EventConstants.UPDATE_EVENT_FROM_DATE,
				EventConstants.UPDATE_EVENT_TO_DATE, EventConstants.EVENT_EVENT_STATE_BAD,
				EventConstants.UPDATE_EVENT_EVENT_TYPE, new HashSet<>(), new HashSet<>(), new HashSet<>());

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + this.authTokenAdmin);
		HttpEntity<EventDTO> req = new HttpEntity<>(event, headers);

		ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.PUT, req, Object.class);
		String message = (String) response.getBody();

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Event state can not be changed!", message);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void update_throwsDataException_hallIsNotAvailable() throws Exception {
		URI uri = new URI(EventConstants.URL_PREFIX);

		EventDTO event = new EventDTO(EventConstants.PERSISTED_EVENT_ID, EventConstants.UPDATE_EVENT_NAME,
				EventConstants.UPDATE_EVENT_DESCRIPTION, EventConstants.PERSISTED_EVENT_FROM_DATE2,
				EventConstants.PERSISTED_EVENT_TO_DATE2, EventConstants.UPDATE_EVENT_EVENT_STATE,
				EventConstants.UPDATE_EVENT_EVENT_TYPE, new HashSet<>(), new HashSet<>(), new HashSet<>());

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + this.authTokenAdmin);
		HttpEntity<EventDTO> req = new HttpEntity<>(event, headers);

		ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.PUT, req, Object.class);
		String message = (String) response.getBody();

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Hall is not available in desired period", message);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void updateHall_valid() throws Exception {
		URI uri = new URI(EventConstants.URL_PREFIX + "/hall");

		HashSet<SectorDTO> sectors = new HashSet<>();
		sectors.add(new SectorDTO(SectorConstants.PERSISTED_SECTOR_ID, "", 0, 0, 0, HallConstants.PERSISTED_HALL_ID));

		HashSet<HallDTO> halls = new HashSet<>();
		halls.add(new HallDTO(HallConstants.PERSISTED_HALL_ID, HallConstants.PERSISTED_HALL_NAME,
				new PlaceDTO(HallConstants.PERSISTED_HALL_PLACE_ID, "", "", 0, 0), sectors));

		HashSet<MediaDTO> mediaList = new HashSet<>();
		mediaList.add(new MediaDTO(null, MediaConstants.NEW_MEDIA_PATH, null));

		HashSet<PriceListDTO> priceLists = new HashSet<>();
		priceLists.add(new PriceListDTO(null, 100.0, null, SectorConstants.PERSISTED_SECTOR_ID));

		EventDTO event = new EventDTO(EventConstants.PERSISTED_EVENT_ID, EventConstants.UPDATE_EVENT_NAME,
				EventConstants.UPDATE_EVENT_DESCRIPTION, EventConstants.UPDATE_EVENT_FROM_DATE,
				EventConstants.UPDATE_EVENT_TO_DATE, EventConstants.UPDATE_EVENT_EVENT_STATE,
				EventConstants.UPDATE_EVENT_EVENT_TYPE, halls, priceLists, mediaList);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + this.authTokenAdmin);
		HttpEntity<EventDTO> req = new HttpEntity<>(event, headers);

		ResponseEntity<EventDTO> response = restTemplate.exchange(uri, HttpMethod.PUT, req, EventDTO.class);
		EventDTO updated = response.getBody();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(EventConstants.PERSISTED_EVENT_ID, updated.getId());
		assertEquals(halls.size(), updated.getHalls().size());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void updateHall_throwsResourceNotFoundException() throws Exception {
		URI uri = new URI(EventConstants.URL_PREFIX + "/hall");

		HashSet<HallDTO> halls = new HashSet<>();
		HashSet<MediaDTO> mediaList = new HashSet<>();
		HashSet<PriceListDTO> priceLists = new HashSet<>();

		EventDTO event = new EventDTO(EventConstants.INVALID_EVENT_ID, EventConstants.UPDATE_EVENT_NAME,
				EventConstants.UPDATE_EVENT_DESCRIPTION, EventConstants.UPDATE_EVENT_FROM_DATE,
				EventConstants.UPDATE_EVENT_TO_DATE, EventConstants.UPDATE_EVENT_EVENT_STATE,
				EventConstants.UPDATE_EVENT_EVENT_TYPE, halls, priceLists, mediaList);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + this.authTokenAdmin);
		HttpEntity<EventDTO> req = new HttpEntity<>(event, headers);

		ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.PUT, req, Object.class);
		String message = (String) response.getBody();

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("Event is not found!", message);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void updateHall_throwsTicketIsBoughtException() throws Exception {
		URI uri = new URI(EventConstants.URL_PREFIX + "/hall");

		HashSet<HallDTO> halls = new HashSet<>();
		HashSet<MediaDTO> mediaList = new HashSet<>();
		HashSet<PriceListDTO> priceLists = new HashSet<>();

		EventDTO event = new EventDTO(EventConstants.PERSISTED_EVENT_ID2, EventConstants.UPDATE_EVENT_NAME,
				EventConstants.UPDATE_EVENT_DESCRIPTION, EventConstants.UPDATE_EVENT_FROM_DATE,
				EventConstants.UPDATE_EVENT_TO_DATE, EventConstants.UPDATE_EVENT_EVENT_STATE,
				EventConstants.UPDATE_EVENT_EVENT_TYPE, halls, priceLists, mediaList);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + this.authTokenAdmin);
		HttpEntity<EventDTO> req = new HttpEntity<>(event, headers);

		ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.PUT, req, Object.class);
		String message = (String) response.getBody();

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Ticket is bought for this event, you can not change the hall!", message);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void updateHall_throwsCollectionIsEmptyException_hall() throws Exception {
		URI uri = new URI(EventConstants.URL_PREFIX + "/hall");

		HashSet<HallDTO> halls = new HashSet<>();
		HashSet<MediaDTO> mediaList = new HashSet<>();
		HashSet<PriceListDTO> priceLists = new HashSet<>();

		EventDTO event = new EventDTO(EventConstants.PERSISTED_EVENT_ID, EventConstants.UPDATE_EVENT_NAME,
				EventConstants.UPDATE_EVENT_DESCRIPTION, EventConstants.UPDATE_EVENT_FROM_DATE,
				EventConstants.UPDATE_EVENT_TO_DATE, EventConstants.UPDATE_EVENT_EVENT_STATE,
				EventConstants.UPDATE_EVENT_EVENT_TYPE, halls, priceLists, mediaList);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + this.authTokenAdmin);
		HttpEntity<EventDTO> req = new HttpEntity<>(event, headers);

		ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.PUT, req, Object.class);
		String message = (String) response.getBody();

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("You must chose one hall", message);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void updateHall_throwsDateException() throws Exception {
		URI uri = new URI(EventConstants.URL_PREFIX + "/hall");

		HashSet<SectorDTO> sectors = new HashSet<>();
		sectors.add(new SectorDTO(SectorConstants.PERSISTED_SECTOR_ID, "", 0, 0, 0, HallConstants.PERSISTED_HALL_ID));

		HashSet<HallDTO> halls = new HashSet<>();
		halls.add(new HallDTO(HallConstants.PERSISTED_HALL_ID, HallConstants.PERSISTED_HALL_NAME,
				new PlaceDTO(HallConstants.PERSISTED_HALL_PLACE_ID, "", "", 0, 0), sectors));

		HashSet<MediaDTO> mediaList = new HashSet<>();
		mediaList.add(new MediaDTO(null, MediaConstants.NEW_MEDIA_PATH, null));

		HashSet<PriceListDTO> priceLists = new HashSet<>();
		priceLists.add(new PriceListDTO(null, 100.0, null, SectorConstants.PERSISTED_SECTOR_ID));

		EventDTO event = new EventDTO(EventConstants.PERSISTED_EVENT_ID, EventConstants.UPDATE_EVENT_NAME,
				EventConstants.UPDATE_EVENT_DESCRIPTION, EventConstants.PERSISTED_EVENT_FROM_DATE2,
				EventConstants.PERSISTED_EVENT_TO_DATE2, EventConstants.UPDATE_EVENT_EVENT_STATE,
				EventConstants.UPDATE_EVENT_EVENT_TYPE, halls, priceLists, mediaList);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + this.authTokenAdmin);
		HttpEntity<EventDTO> req = new HttpEntity<>(event, headers);

		ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.PUT, req, Object.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		String message = (String) response.getBody();
		assertEquals("Hall is not available in desired period", message);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void updateHall_throwsCollectionIsEmptyException_sector() throws Exception {
		URI uri = new URI(EventConstants.URL_PREFIX + "/hall");

		HashSet<SectorDTO> sectors = new HashSet<>();

		HashSet<HallDTO> halls = new HashSet<>();
		halls.add(new HallDTO(HallConstants.PERSISTED_HALL_ID, HallConstants.PERSISTED_HALL_NAME,
				new PlaceDTO(HallConstants.PERSISTED_HALL_PLACE_ID, "", "", 0, 0), sectors));

		HashSet<MediaDTO> mediaList = new HashSet<>();
		mediaList.add(new MediaDTO(null, MediaConstants.NEW_MEDIA_PATH, null));

		HashSet<PriceListDTO> priceLists = new HashSet<>();
		priceLists.add(new PriceListDTO(null, 100.0, null, SectorConstants.PERSISTED_SECTOR_ID));

		EventDTO event = new EventDTO(EventConstants.PERSISTED_EVENT_ID, EventConstants.UPDATE_EVENT_NAME,
				EventConstants.UPDATE_EVENT_DESCRIPTION, EventConstants.UPDATE_EVENT_FROM_DATE,
				EventConstants.UPDATE_EVENT_TO_DATE, EventConstants.UPDATE_EVENT_EVENT_STATE,
				EventConstants.UPDATE_EVENT_EVENT_TYPE, halls, priceLists, mediaList);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + this.authTokenAdmin);
		HttpEntity<EventDTO> req = new HttpEntity<>(event, headers);

		ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.PUT, req, Object.class);
		String message = (String) response.getBody();

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("You must chose one sector", message);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void updateHall_throwsSectorIsNotInThisHallException() throws Exception {
		URI uri = new URI(EventConstants.URL_PREFIX + "/hall");

		HashSet<SectorDTO> sectors = new HashSet<>();
		sectors.add(new SectorDTO(SectorConstants.PERSISTED_SECTOR_ID3, "", 0, 0, 0, HallConstants.PERSISTED_HALL_ID2));

		HashSet<HallDTO> halls = new HashSet<>();
		halls.add(new HallDTO(HallConstants.PERSISTED_HALL_ID, HallConstants.PERSISTED_HALL_NAME,
				new PlaceDTO(HallConstants.PERSISTED_HALL_PLACE_ID, "", "", 0, 0), sectors));

		HashSet<MediaDTO> mediaList = new HashSet<>();
		mediaList.add(new MediaDTO(null, MediaConstants.NEW_MEDIA_PATH, null));

		HashSet<PriceListDTO> priceLists = new HashSet<>();
		priceLists.add(new PriceListDTO(null, 100.0, null, SectorConstants.PERSISTED_SECTOR_ID));

		EventDTO event = new EventDTO(EventConstants.PERSISTED_EVENT_ID, EventConstants.UPDATE_EVENT_NAME,
				EventConstants.UPDATE_EVENT_DESCRIPTION, EventConstants.UPDATE_EVENT_FROM_DATE,
				EventConstants.UPDATE_EVENT_TO_DATE, EventConstants.UPDATE_EVENT_EVENT_STATE,
				EventConstants.UPDATE_EVENT_EVENT_TYPE, halls, priceLists, mediaList);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + this.authTokenAdmin);
		HttpEntity<EventDTO> req = new HttpEntity<>(event, headers);

		ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.PUT, req, Object.class);
		String message = (String) response.getBody();

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Sector is not in chosen hall!", message);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void updateHall_throwsSectorPriceListException() throws Exception {
		URI uri = new URI(EventConstants.URL_PREFIX + "/hall");

		HashSet<SectorDTO> sectors = new HashSet<>();
		sectors.add(new SectorDTO(SectorConstants.PERSISTED_SECTOR_ID, "", 0, 0, 0, HallConstants.PERSISTED_HALL_ID));

		HashSet<HallDTO> halls = new HashSet<>();
		halls.add(new HallDTO(HallConstants.PERSISTED_HALL_ID, HallConstants.PERSISTED_HALL_NAME,
				new PlaceDTO(HallConstants.PERSISTED_HALL_PLACE_ID, "", "", 0, 0), sectors));

		HashSet<MediaDTO> mediaList = new HashSet<>();
		mediaList.add(new MediaDTO(null, MediaConstants.NEW_MEDIA_PATH, null));

		HashSet<PriceListDTO> priceLists = new HashSet<>();
		priceLists.add(new PriceListDTO(null, 100.0, null, SectorConstants.PERSISTED_SECTOR_ID2));

		EventDTO event = new EventDTO(EventConstants.PERSISTED_EVENT_ID, EventConstants.UPDATE_EVENT_NAME,
				EventConstants.UPDATE_EVENT_DESCRIPTION, EventConstants.UPDATE_EVENT_FROM_DATE,
				EventConstants.UPDATE_EVENT_TO_DATE, EventConstants.UPDATE_EVENT_EVENT_STATE,
				EventConstants.UPDATE_EVENT_EVENT_TYPE, halls, priceLists, mediaList);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + this.authTokenAdmin);
		HttpEntity<EventDTO> req = new HttpEntity<>(event, headers);

		ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.PUT, req, Object.class);
		String message = (String) response.getBody();

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Sector must have price list!", message);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void updateHall_throwsSectorCapacatyMustBePositiveNumberException() throws Exception {
		URI uri = new URI(EventConstants.URL_PREFIX + "/hall");

		HashSet<SectorDTO> sectors = new HashSet<>();
		sectors.add(new SectorDTO(SectorConstants.PERSISTED_SECTOR_ID2, "", 0, 0, 0, HallConstants.PERSISTED_HALL_ID));

		HashSet<HallDTO> halls = new HashSet<>();
		halls.add(new HallDTO(HallConstants.PERSISTED_HALL_ID, HallConstants.PERSISTED_HALL_NAME,
				new PlaceDTO(HallConstants.PERSISTED_HALL_PLACE_ID, "", "", 0, 0), sectors));

		HashSet<MediaDTO> mediaList = new HashSet<>();
		mediaList.add(new MediaDTO(null, MediaConstants.NEW_MEDIA_PATH, null));

		HashSet<PriceListDTO> priceLists = new HashSet<>();
		priceLists.add(new PriceListDTO(null, 100.0, null, SectorConstants.PERSISTED_SECTOR_ID2));

		EventDTO event = new EventDTO(EventConstants.PERSISTED_EVENT_ID, EventConstants.UPDATE_EVENT_NAME,
				EventConstants.UPDATE_EVENT_DESCRIPTION, EventConstants.UPDATE_EVENT_FROM_DATE,
				EventConstants.UPDATE_EVENT_TO_DATE, EventConstants.UPDATE_EVENT_EVENT_STATE,
				EventConstants.UPDATE_EVENT_EVENT_TYPE, halls, priceLists, mediaList);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + this.authTokenAdmin);
		HttpEntity<EventDTO> req = new HttpEntity<>(event, headers);

		ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.PUT, req, Object.class);
		String message = (String) response.getBody();

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Sector capacity must be positiv number!", message);
	}
}
