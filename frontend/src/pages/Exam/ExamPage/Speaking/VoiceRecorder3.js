import { useEffect, useState } from "react";

const VoiceRecorder3 = () => {
  let recordingTimeMS = 3000;

  const audioInfo = {
    preview: {
      srcObject: "",
      captureStream: "",
    },
    downloadButton: {
      href: "",
    },
    recording: {
      src: "",
    },
  };

  const [num, setNum] = useState(1);

  function wait(delayInMS) {
    console.log(delayInMS);
    return new Promise((resolve) => setTimeout(resolve, delayInMS));
  }

  // 녹화 시작 함수
  function startRecording(stream, lengthInMS) {
    let recorder = new MediaRecorder(stream);

    let data = [];
    recorder.ondataavailable = (event) => data.push(event.data);
    recorder.start();

    let stopped = new Promise((resolve, reject) => {
      recorder.onstop = resolve;
      recorder.onerror = (event) => reject(event.name);
    });

    let recorded = wait(lengthInMS).then(() => {
      if (recorder.state === "recording") {
        recorder.stop();
        setNum(0);
      }
    });
    return Promise.all([stopped, recorded]).then(() => data);
  }

  // 녹화 종료 함수
  function stop(st) {
    // stream.getTracks().forEach((track) => track.stop());
    console.log(st);
    setNum(0);
  }

  // 녹화 시작 버튼 클릭
  const isClickStartBtn = () => {
    navigator.mediaDevices
      .getUserMedia({
        video: true,
        audio: true,
      })
      .then((stream) => {
        audioInfo.preview.srcObject = stream;
        audioInfo.downloadButton.href = stream;

        console.log(audioInfo.preview.srcObject);
        // return new Promise((resolve) => (preview.onplaying = resolve));
      })
      .then(() =>
        startRecording(audioInfo.preview.captureStream(), recordingTimeMS)
      )
      .then((recordedChunks) => {
        let recordedBlob = new Blob(recordedChunks, { type: "video/webm" });
        console.log(recordedBlob);
        audioInfo.recording.src = URL.createObjectURL(recordedBlob);
        audioInfo.downloadButton.href = audioInfo.recording.src;
        console.log(recordedBlob);
      })
      .catch((error) => {
        if (error.name === "NotFoundError") {
        } else {
        }
      });
  };

  const isClickStopBtn = () => {
    stop(audioInfo.preview.srcObject);
  };

  console.log(audioInfo.preview.srcObject);

  return (
    <div>
      <button onClick={isClickStartBtn}>녹음시작</button>
      <button onClick={isClickStopBtn}>녹음종료</button>

      {num === 1 ? "" : <video src={audioInfo.preview.srcObject}></video>}
    </div>
  );
};

export default VoiceRecorder3;
