import cv2
import mediapipe as mp
import math

mp_drawing = mp.solutions.drawing_utils
mp_pose = mp.solutions.pose


def calculate_angle(a, b, c):
    ax, ay = a
    bx, by = b
    cx, cy = c

    radians = math.atan2(cy - by, cx - bx) - math.atan2(ay - by, ax - bx)
    angle = abs(radians * 180.0 / math.pi)

    if angle > 180:
        angle = 360 - angle

    return angle


counter = 0
stage = None

cap = cv2.VideoCapture(0)

with mp_pose.Pose(
        min_detection_confidence=0.5,
        min_tracking_confidence=0.5
) as pose:

    while cap.isOpened():
        ret, frame = cap.read()

        if not ret:
            break

        image = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
        image.flags.writeable = False

        results = pose.process(image)

        image.flags.writeable = True
        image = cv2.cvtColor(image, cv2.COLOR_RGB2BGR)

        try:
            landmarks = results.pose_landmarks.landmark

            shoulder = [
                landmarks[
                    mp_pose.PoseLandmark.LEFT_SHOULDER.value
                ].x,
                landmarks[
                    mp_pose.PoseLandmark.LEFT_SHOULDER.value
                ].y
            ]

            elbow = [
                landmarks[
                    mp_pose.PoseLandmark.LEFT_ELBOW.value
                ].x,
                landmarks[
                    mp_pose.PoseLandmark.LEFT_ELBOW.value
                ].y
            ]

            wrist = [
                landmarks[
                    mp_pose.PoseLandmark.LEFT_WRIST.value
                ].x,
                landmarks[
                    mp_pose.PoseLandmark.LEFT_WRIST.value
                ].y
            ]

            angle = calculate_angle(
                shoulder,
                elbow,
                wrist
            )

            cv2.putText(
                image,
                str(int(angle)),
                tuple((
                    int(elbow[0] * 640),
                    int(elbow[1] * 480)
                )),
                cv2.FONT_HERSHEY_SIMPLEX,
                1,
                (255, 255, 255),
                2,
                cv2.LINE_AA
            )

            if angle > 160:
                stage = "up"

            if angle < 90 and stage == "up":
                stage = "down"
                counter += 1
                print("Pushups:", counter)

        except:
            pass

        # ================= UI BOX =================

        cv2.rectangle(
            image,
            (0, 0),
            (280, 90),
            (245, 117, 16),
            -1
        )

        cv2.putText(
            image,
            "GYMQUEST PUSHUPS",
            (15, 20),
            cv2.FONT_HERSHEY_SIMPLEX,
            0.6,
            (0, 0, 0),
            2,
            cv2.LINE_AA
        )

        cv2.putText(
            image,
            str(counter),
            (20, 70),
            cv2.FONT_HERSHEY_SIMPLEX,
            2,
            (255, 255, 255),
            3,
            cv2.LINE_AA
        )

        cv2.imshow(
            "GymQuest AI Pushup Counter",
            image
        )

        key = cv2.waitKey(10) & 0xFF

        if key == ord("q"):
            break

cap.release()
cv2.destroyAllWindows()

# ================= SAVE RESULT =================

with open("pushup_result.txt", "w") as file:
    file.write(str(counter))

print("Final Pushup Count:", counter)
print("Saved to pushup_result.txt")
print("Use this count in GymQuest JavaFX app")