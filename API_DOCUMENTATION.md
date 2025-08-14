# Mobile API Documentation

This document describes the REST API endpoints for the mobile applications.

## Base URL
```
http://dashboard.localhost:8000/api/v1/  # Development
https://dashboard.your-domain.com/api/v1/  # Production
```

## Authentication

### Login
**POST** `/auth/`

**Request Body:**
```json
{
    "username": "student@example.com",
    "password": "password123"
}
```

**Response:**
```json
{
    "id": 1,
    "username": "student@example.com",
    "email": "student@example.com",
    "first_name": "John",
    "last_name": "Doe",
    "date_joined": "2024-01-01T00:00:00Z",
    "user_type": "student"
}
```

### Logout
**DELETE** `/auth/`

**Response:**
```json
{
    "message": "Logged out successfully"
}
```

## Student Endpoints

### Dashboard
**GET** `/student/dashboard/`

**Response:**
```json
{
    "id": 1,
    "user": {
        "id": 1,
        "username": "student@example.com",
        "email": "student@example.com",
        "first_name": "John",
        "last_name": "Doe"
    },
    "current_level": {
        "id": 1,
        "level_code": "A2",
        "level_name": "Elementary"
    },
    "progress_percentage": 75,
    "classes": [...],
    "upcoming_meetings": [...],
    "recent_sessions": [...]
}
```

### Profile
**GET** `/student/profile/`

**PUT** `/student/profile/`

**Request Body:**
```json
{
    "user": {
        "first_name": "John",
        "last_name": "Doe",
        "email": "john.doe@example.com"
    },
    "student": {
        "phone": "+1234567890"
    }
}
```

### Classes
**GET** `/student/classes/` - List all classes

**GET** `/student/class/{class_id}/` - Get class details

### Sessions
**GET** `/student/sessions/` - List all sessions

**GET** `/student/class/{class_id}/session/{session_id}/` - Get session details

### Session Content
**GET** `/student/content/` - List all content

**GET** `/student/content/{content_id}/vocabulary/` - Get vocabulary for session

**GET** `/student/content/{content_id}/exercises/` - Get exercises for session

**GET** `/student/content/{content_id}/grammar/` - Get grammar content

**GET** `/student/content/{content_id}/flashcards/` - Get flashcards

### Meetings
**GET** `/student/meetings/` - List all meetings

**POST** `/student/meetings/{meeting_id}/join/` - Join a meeting

### Invitations
**GET** `/student/invitations/` - List all invitations

**POST** `/student/invitations/{invitation_id}/accept/` - Accept invitation

**POST** `/student/invitations/{invitation_id}/decline/` - Decline invitation

### Progress
**GET** `/student/progress/` - List progress records

## Teacher Endpoints (Future Use)

### Dashboard
**GET** `/teacher/dashboard/`

## Error Responses

All endpoints return standard HTTP status codes:

- `200` - Success
- `400` - Bad Request
- `401` - Unauthorized
- `403` - Forbidden
- `404` - Not Found
- `500` - Internal Server Error

**Error Response Format:**
```json
{
    "error": "Error message description"
}
```

## Authentication

All endpoints (except login/logout) require authentication. Include session cookies or use token authentication.

## Pagination

List endpoints support pagination with the following parameters:
- `page` - Page number (default: 1)
- `page_size` - Items per page (default: 20)

**Pagination Response:**
```json
{
    "count": 100,
    "next": "http://api.example.com/endpoint/?page=2",
    "previous": null,
    "results": [...]
}
```

## Example Usage

### Login and Get Dashboard
```bash
# Login
curl -X POST https://your-domain.com/api/v1/auth/ \
  -H "Content-Type: application/json" \
  -d '{"username": "student@example.com", "password": "password123"}' \
  -c cookies.txt

# Get Dashboard
curl -X GET https://your-domain.com/api/v1/student/dashboard/ \
  -b cookies.txt
```

### Get Session Content
```bash
curl -X GET https://your-domain.com/api/v1/student/content/1/vocabulary/ \
  -b cookies.txt
```

## Mobile App Integration

For mobile apps, you can use these endpoints to:

1. **Authentication**: Use login endpoint to authenticate users
2. **Dashboard**: Display student overview with classes, meetings, and progress
3. **Content**: Access AI-generated learning materials (vocabulary, exercises, grammar)
4. **Meetings**: Join live Zoom sessions
5. **Progress**: Track learning progress over time

The API is designed to be RESTful and follows standard conventions for easy integration with mobile frameworks like React Native, Flutter, or Kotlin Multiplatform. 