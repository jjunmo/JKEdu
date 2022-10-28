import { useEffect, useState } from "react";

const VoiceRecorder4 = () => {
  const [recording, setRecord] = useState();
  let stream;
  let chunks = [];
  let mediaRecorder;

  const [audioURL, setAudioURL] = useState();

  const permissonRecord = async () => {
    stream = await navigator.mediaDevices.getUserMedia({ audio: true });
    mediaRecorder = new MediaRecorder(stream);
    mediaRecorder.ondataavailable = (e) => {
      if (e.data && e.data.size > 0) {
        chunks.push(e.data);
      }
    };
  };

  const startRecord = async (e) => {
    e.preventDefault();
    mediaRecorder.start(10);
    setRecord(true);

    setTimeout(() => {
      mediaRecorder.stop();
      saveAudio();
    }, 3000);
  };

  const saveAudio = () => {
    const blob = new Blob(chunks, { type: "audio/mp3" });
    const blobUrl = URL.createObjectURL(blob);

    setAudioURL(blobUrl);
    setRecord(false);
  };

  useEffect(() => {
    permissonRecord();
  }, []);

  return (
    <div>
      <button onClick={startRecord}>시작</button>
      {recording === false ? (
        <div>
          <audio src={audioURL} controls></audio>
        </div>
      ) : (
        ""
      )}
    </div>
  );
};

export default VoiceRecorder4;
