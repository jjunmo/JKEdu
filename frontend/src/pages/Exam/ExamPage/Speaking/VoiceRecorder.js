import { useState } from "react";
import styled from "styled-components";
import { ReactMediaRecorder } from "react-media-recorder";

const Main = styled.div`
  margin-top: 25px;
`;

const StartBtn = styled.button.attrs((prop) => ({
  disabled: prop.click === true ? true : false,
}))`
  box-sizing: border-box;
  width: 110px;
  height: 40px;
  text-align: center;
  border: none;
  border-radius: 15px;
  font-size: 15px;
  font-family: SCDream5;
  color: white;
  background-color: ${(prop) => (prop.click === true ? "#EAEAEA" : "#1e1d4d")};
  text-decoration: none;

  cursor: ${(prop) => (prop.click === true ? "not-allowed" : "pointer")};
`;

const StopBtn = styled(StartBtn).attrs((prop) => ({
  disabled: prop.click === true ? false : true,
}))`
  background-color: ${(prop) => (prop.click === true ? "#1e1d4d" : "#EAEAEA")};
  cursor: ${(prop) => (prop.click === true ? "pointer" : "not-allowed")};
`;

const VoiceRecorder = () => {
  const [isClickRecord, setIsClickRecord] = useState(false);

  // 녹음중인지 확인하는 함수
  // 녹음중이라면 녹음 시작 버튼을 클릭 못하게 변경
  const recording = (e) => {
    setIsClickRecord((prev) => !prev);
  };

  // 녹음한 음성을 서버로 보내기 위한 함수
  const makeSoundFile = (blob) => {
    const sound = new File(
      [blob],
      "soundBlob",
      { lastModified: new Date().getTime() },
      [blob]
    );

    console.log(sound);
  };

  console.log(isClickRecord);

  return (
    <Main>
      <ReactMediaRecorder
        audio
        render={({ status, startRecording, stopRecording, mediaBlobUrl }) => (
          <div>
            <StartBtn
              onClick={() => {
                startRecording();
                recording();
              }}
              click={isClickRecord}
            >
              녹음 시작
            </StartBtn>
            <StopBtn
              onClick={() => {
                stopRecording();
                recording();
              }}
              click={isClickRecord}
            >
              녹음 종료
            </StopBtn>
            <div>
              {mediaBlobUrl === undefined ? (
                ""
              ) : (
                <div>
                  <h3>아래는 녹음 내용입니다.</h3>
                  <audio src={mediaBlobUrl} controls />
                  {makeSoundFile(mediaBlobUrl)}
                  <div>
                    <a href={mediaBlobUrl} download>
                      다운로드
                    </a>
                  </div>
                </div>
              )}
            </div>
          </div>
        )}
      />
    </Main>
  );
};

export default VoiceRecorder;
