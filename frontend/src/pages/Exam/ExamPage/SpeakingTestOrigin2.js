import { useState } from "react";
import React from "react";
import styled from "styled-components";
import { Link, useParams } from "react-router-dom";

import "App.css";

const WrapperDiv = styled.div`
  width: 1080px;
  height: calc(100% - 80px);
  display: flex;
  align-items: center;
  justify-content: center;
`;

const AllDiv = styled.div`
  font-family: SCDream5;
  font-size: 18px;
  margin: 0 auto;
  box-sizing: border-box;
  width: 80%;
  height: 90%;

  align-content: center;
  justify-content: baseline;
  animation-duration: 0ms;
`;

const LevelTestDiv = styled.div`
  font-size: 25px;
  border-bottom: 5px solid #ffd8a9;
`;

const PartH2 = styled.h2`
  margin: 0;
`;

const MainTextDiv = styled.div`
  margin-top: 10px;
`;

const QuestionContentDiv = styled(LevelTestDiv)`
  font-size: 20px;
  margin-top: 10px;
  font-family: Arial;
  border: none;
  word-break: break-all;
`;

const AnswerAllDiv = styled.div``;

const AnswerDiv = styled.div`
  height: 40px;
`;

// 문제의 보기를 보여주는 span
const AnswerText = styled.span`
  box-sizing: border-box;
  margin: 10 0 10 0;
  font-size: 25px;
  font-family: Arial;
  font-weight: bolder;
  cursor: pointer;
`;

// 문제의 보기 번호를 나타내는 span
const AnswerNumber = styled.span``;

const GetNextProblemButton = styled.button.attrs((prop) => ({}))`
  border: none;
  border-radius: 15px;
  font-family: jua;
  font-size: 15px;
  padding: 3px 30px 3px 30px;
  background-color: ${(prop) => (prop.answer === "" ? "#CFD2CF" : "#97d2ec")};
  margin: 15px 0px 0px 0px;
  text-decoration: none;
  color: black;
  &:hover {
    cursor: pointer;
  }
  pointer-events: ${(prop) => (prop.answer === "" ? "none" : "")};
`;

const GetResultPage = styled(Link).attrs((prop) => ({}))`
  border: none;
  border-radius: 15px;
  font-family: jua;
  font-size: 15px;
  padding: 3px 30px 3px 30px;
  background-color: ${(prop) => (prop.answer === "" ? "#CFD2CF" : "#97d2ec")};
  margin: 15px 0px 0px 0px;
  text-decoration: none;
  color: black;
  display: inline-block;
  &:hover {
    cursor: pointer;
  }
  pointer-events: ${(prop) => (prop.answer === "" ? "none" : "")};
`;

const NarrativeInput = styled.input`
  width: 300px;
  height: 32px;
  margin-top: 20px;
  font-size: 16px;
  border: none;
  border-radius: 15px;
  padding-left: 15px;
  box-sizing: border-box;
  background-color: #cfd2cf;
`;

const initialState = [
  {
    q: "다음 사진과 일치하는 단어를 고르시오.",
    example: ["finger", "fish", "full", "fun"],
    answer: "finger",
    category: "multiple-choice",
    havePicture: true,
    src: "https://dictionary.cambridge.org/ko/images/thumb/finger_noun_002_13894.jpg?version=5.0.250",
  },
  {
    q: "다음 빈칸에 알맞은 단어를 쓰시오.",
    mainText: "We ______ boys.",
    answer: "are",
    category: "narrative",
  },
  {
    q: "다음을 듣고 알맞은 것을 고르시오.",
    example: ["n", "a", "b", "c"],
    answer: "n",
    category: "multiple-choice",
    url: "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
  },
  {
    q: "다음 사진에 알맞은 단어를 쓰시오.",
    answer: "n",
    category: "narrative",
    havePicture: true,
    src: "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=http%3A%2F%2Fcfile2.uf.tistory.com%2Fimage%2F2408DD3658A648B12CF09D",
  },
];

const SpeakingTestOrigin2 = () => {
  // 현재 응시중인 영역
  const testPart = useParams().part;

  // 현재 몇 단계 문제인지 확인하기 위해 사용
  const [questionNum, setQuestionNum] = useState(0);

  // 객관식이면 정답이 뭔지, 주관식이면 서술형 답을 저장
  const [answer, setAnswer] = useState("");

  // 객관식 정답 번호 저장하는 변수 : 화면에 정답 체크를 표시하기 위해
  const [answerNum, setAnswerNum] = useState("");

  const getNextQuestion = (e) => {
    e.preventDefault();
    setQuestionNum((prev) => prev + 1);
    setAnswer("");
    setAnswerNum("");
  };

  // 객관식 번호를 설정하거나
  const selectAnswer = (answer, number) => {
    setAnswer(answer);
    setAnswerNum(number);
  };

  // 주관식 정답 변경
  const changeAnswer = (e) => {
    const { value } = e.target;
    setAnswer(value);
  };

  console.log(answerNum);

  return (
    <WrapperDiv>
      <AllDiv>
        <LevelTestDiv>
          <PartH2>Part1. {testPart}</PartH2>
        </LevelTestDiv>
        <QuestionContentDiv>
          Q{questionNum + 1}. {initialState[questionNum].q}
          {initialState[questionNum].mainText !== undefined ? (
            <MainTextDiv>{initialState[questionNum].mainText}</MainTextDiv>
          ) : (
            ""
          )}
        </QuestionContentDiv>
        {initialState[questionNum].url !== undefined ? (
          <audio controls>
            <source
              src="https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
              type="audio/mp3"
            />
          </audio>
        ) : (
          ""
        )}
        {initialState[questionNum].havePicture === true ? (
          <img
            src={initialState[questionNum].src}
            alt="test-img"
            style={{ width: "300px", height: "200px" }}
          />
        ) : (
          ""
        )}
        {initialState[questionNum].category === "multiple-choice" ? (
          <AnswerAllDiv>
            <AnswerDiv>
              <AnswerText
                onClick={() =>
                  selectAnswer(initialState[questionNum].example[0], "1")
                }
              >
                <AnswerNumber>
                  {answerNum === "1" ? (
                    <img
                      src="/img/test/check.png"
                      style={{ width: "20px", height: "20px" }}
                      alt="check"
                    />
                  ) : (
                    "1. "
                  )}
                </AnswerNumber>
                {initialState[questionNum].example[0]}
              </AnswerText>
            </AnswerDiv>
            <AnswerDiv>
              <AnswerText
                onClick={() =>
                  selectAnswer(initialState[questionNum].example[1], "2")
                }
              >
                <AnswerNumber>
                  {" "}
                  {answerNum === "2" ? (
                    <img
                      src="/img/test/check.png"
                      style={{ width: "20px", height: "20px" }}
                      alt="check"
                    />
                  ) : (
                    "2. "
                  )}
                </AnswerNumber>
                {initialState[questionNum].example[1]}
              </AnswerText>
            </AnswerDiv>{" "}
            <AnswerDiv>
              <AnswerText
                onClick={() =>
                  selectAnswer(initialState[questionNum].example[2], "3")
                }
              >
                <AnswerNumber>
                  {" "}
                  {answerNum === "3" ? (
                    <img
                      src="/img/test/check.png"
                      style={{ width: "20px", height: "20px" }}
                      alt="check"
                    />
                  ) : (
                    "3. "
                  )}
                </AnswerNumber>

                {initialState[questionNum].example[2]}
              </AnswerText>
            </AnswerDiv>{" "}
            <AnswerDiv>
              <AnswerText
                onClick={() =>
                  selectAnswer(initialState[questionNum].example[3], "4")
                }
              >
                <AnswerNumber>
                  {" "}
                  {answerNum === "4" ? (
                    <img
                      src="/img/test/check.png"
                      style={{ width: "20px", height: "20px" }}
                      alt="check"
                    />
                  ) : (
                    "4. "
                  )}
                </AnswerNumber>
                {initialState[questionNum].example[3]}
              </AnswerText>
            </AnswerDiv>
          </AnswerAllDiv>
        ) : (
          ""
        )}
        {initialState[questionNum].category === "narrative" ? (
          <div>
            <NarrativeInput onChange={changeAnswer}></NarrativeInput>
          </div>
        ) : (
          ""
        )}
        {questionNum < initialState.length - 1 ? (
          <GetNextProblemButton onClick={getNextQuestion} answer={answer}>
            다음
          </GetNextProblemButton>
        ) : (
          <GetResultPage to="/select-test" answer={answer}>
            제출하기
          </GetResultPage>
        )}
      </AllDiv>
    </WrapperDiv>
  );
};

export default SpeakingTestOrigin2;
