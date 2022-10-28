import { useState, useRef } from "react";
import produce from "immer";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";

import "App.css";

import exams from "db/exam_dummy/listening.json";

const Main = styled.div`
  width: 100%;
  height: calc(100% - 80px);
  display: flex;
  font-family: SCDream5;
`;

const Wrapper = styled.div`
  box-sizing: border-box;
  width: 1080px;
  margin: 0 auto;
`;

const PartTitle = styled.div`
  box-sizing: border-box;
  margin-top: 50px;
  font-size: 40px;
  border-bottom: 5px solid grey;
`;

const ExamQuestion = styled.div`
  box-sizing: border-box;
  font-size: 35px;
  margin-top: 20px;
`;
const ExamExample = styled.div`
  box-sizing: border-box;
  width: 1000px;
  font-size: 30px;
  margin-top: 20px;
  cursor: pointer;

  &:hover {
    border: 2px solid grey;
    border-radius: 10px;
    background-color: #ededed;
  }
`;

const ButtonDiv = styled.div`
  box-sizing: border-box;
  margin-top: 25px;
  text-align: center;
`;

const NextProblemButton = styled.button`
  box-sizing: border-box;
  width: 150px;
  padding: 10px 30px 10px 30px;
  text-align: center;
  border: none;
  border-radius: 15px;
  font-size: 15px;
  font-family: SCDream5;
  color: white;
  background-color: #1e1d4d;
  text-decoration: none;
  &:hover {
    cursor: pointer;
  }
`;

const PlayImg = styled.img.attrs(() => ({
  src: "/img/test/headphone.png",
}))`
  width: 50px;
  height: 50px;
  cursor: pointer;
  margin-right: 20px;
`;
const PuaseImg = styled(PlayImg).attrs(() => ({
  src: "/img/test/pause.png",
}))``;

const ListeningExamOrigin2 = () => {
  const navi = useNavigate();

  // 오디오 재생을 위한 ref
  const audioRef = useRef();

  // explain dummy에서 시험문제 가져오기
  const exam = exams.exam;

  // number : 몇 번째 문제
  // answer : 정답 (주관식이면 서술형 답, 객관식이면 보기를 저장)
  // answerNum : 몇 번째 보기인지
  const [examInfo, setExamInfo] = useState({
    number: 1,
    answer: "",
    answerNum: "",
  });

  const selectAnswer = (answer) => {
    setExamInfo({ ...examInfo, answerNum: answer });
  };

  const getNextQuestion = (e) => {
    e.preventDefault();

    // 정답 체크여부
    let setAnswer = false;

    // 정답을 체크하지 않았을 때
    if (examInfo.answerNum === "") {
      // 넘어가기를 누르면
      if (
        window.confirm(
          "정답을 작성(선택)하지 않으셨습니다.\n정말로 넘어가시겠습니까?"
        )
      ) {
        setAnswer = true;
      }
    }
    // 정답을 체크했을 때
    else {
      setAnswer = true;
    }

    if (setAnswer === true) {
      setExamInfo(
        produce((draft) => {
          draft.number += 1;
          draft.answer = "";
          draft.answerNum = "";
        })
      );
    }

    //모든 문제를 풀었으면 다른 영역 선택하는 화면으로 이동
    if (exam[examInfo.number] === undefined) {
      navi("/select-exam");
    }
  };

  // 오디오 재생
  const playAudio = () => {
    audioRef.current.play();
  };
  // 오디오 멈춤
  const pauseAudio = () => {
    audioRef.current.pause();
  };

  // exam[examInfo.number -1] : examInfo.number에는 현재 몇 번째 문제를 풀고 있는지 나타내는데
  // 배열은 0부터 시작하니 현재 문제 번호 -1이 현재 풀고 있는 문제를 나타낸다.
  // number을 일부러 1부터 시작하게했음, 추후에 변경 필요시 0부터 시작하게 변경할 예정
  return (
    <Main>
      <Wrapper>
        <PartTitle>Part : Listening</PartTitle>
        <ExamQuestion>
          {examInfo.number}. {exam[examInfo.number - 1].question}
        </ExamQuestion>

        <audio ref={audioRef}>
          <source src={exam[examInfo.number - 1].url} type="audio/mp3" />
        </audio>

        <PlayImg onClick={playAudio}></PlayImg>
        <PuaseImg onClick={pauseAudio}></PuaseImg>

        {exam[examInfo.number - 1].example.map((example, index) => (
          <ExamExample key={example} onClick={() => selectAnswer(index + 1)}>
            {index + 1 === examInfo.answerNum ? (
              <img
                src="/img/test/check.png"
                style={{ width: "30px", height: "30px" }}
                alt="check"
              />
            ) : (
              <span>{index + 1}. </span>
            )}
            {example}
          </ExamExample>
        ))}
        <ButtonDiv>
          <NextProblemButton onClick={getNextQuestion}>
            다음 문제
          </NextProblemButton>
        </ButtonDiv>
      </Wrapper>
    </Main>
  );
};

export default ListeningExamOrigin2;
