import { useEffect, useState } from "react";

const VoiceRecorder2 = () => {
  // 마이크 권한 확인
  let stream = null;
  let mediaRecorder;

  // 음성 저장
  let chunks = [];
  const [audioURL, setAudioURL] = useState("");

  const getMedia = async () => {
    try {
      stream = await navigator.mediaDevices
        .getUserMedia({ audio: true })
        .then((stream) => {
          mediaRecorder = new MediaRecorder(stream);
        });
      /* use the stream */
    } catch (err) {
      window.alert("마이크 권한을 허용하세요.");
      /* handle the error */
      console.log(err);
    }
  };

  const recordStart = () => {
    if (stream !== null) {
      let recorder = new MediaRecorder(stream);
      let data = [];
      recorder.ondataavailable = (e) => {
        data.push(e.data);
        recorder.start();
      }

      // mediaRecorder.start();
      // console.log(mediaRecorder.state);
      // mediaRecorder.ondataavailable = (e) => {
      //   console.log(e.data);
      //   chunks.push(e.data);
      // };
      // console.log(chunks);

      // // create web audio api context
      // const audioCtx = new (window.AudioContext || window.webkitAudioContext)();
      // // create Oscillator node
      // const oscillator = audioCtx.createOscillator();
      // oscillator.type = "square";
      // oscillator.frequency.setValueAtTime(440, audioCtx.currentTime); // value in hertz
      // oscillator.connect(audioCtx.destination);
      // oscillator.start();
      // const makeSound = (stream) => {};
      // makeSound(stream);
    } else {
      window.alert("마이크 권한이 없습니다. 권한을 허용해주세요.");
    }
  };

  const recordStop = () => {
    mediaRecorder.stop();

    const blob = new Blob(chunks, { type: "audio/ogg; codecs=opus" });

    console.log(blob);

    chunks = [];
    setAudioURL(window.URL.createObjectURL(blob));
  };

  // 첫 실행시에만 음성 권한 확인하기
  useEffect(() => {
    getMedia();
  }, []);

  return (
    <div style={{ marginTop: "40px" }}>
      <h2>This is voice recorder2</h2>
      <button onClick={recordStart}>녹음시작</button>
      <button onClick={recordStop}>녹음종료 </button>

      {audioURL === "" ? (
        ""
      ) : (
        <audio controls>
          <source src={audioURL} type="audio/ogg" />
        </audio>
      )}
    </div>
  );
};

export default VoiceRecorder2;
